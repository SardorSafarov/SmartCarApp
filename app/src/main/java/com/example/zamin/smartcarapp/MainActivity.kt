package com.example.zamin.smartcarapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.zamin.smartcarapp.adapter.ViewPageAdapter
import com.example.zamin.smartcarapp.databinding.ActivityMainBinding
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper
import com.example.zamin.smartcarapp.fragment.Page1Fragment
import com.example.zamin.smartcarapp.fragment.Page2Fragment
import com.example.zamin.smartcarapp.settings.SettingsActivity
import com.example.zamin.smartcarapp.utils.*
import com.example.zamin.smartcarapp.utils.AppVarebles.CAR_ABOUT
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity(), Page1Fragment.Page1Interfase,
    Page2Fragment.Page2Interface {
    private lateinit var binding: ActivityMainBinding
    val items: ArrayList<Fragment> = arrayListOf(Page1Fragment(this), Page2Fragment(this))
    lateinit var adapterFragment: ViewPageAdapter
    lateinit var sharedPereferenseHelper: SharedPereferenseHelper
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkSendMessagePerimetion()
        sharedPereferenseHelper = SharedPereferenseHelper((this))
        adapterFragment = ViewPageAdapter(items, this)
        binding.viewPage2.adapter = adapterFragment
        binding.indicator.setViewPager(binding.viewPage2)
        changePhoneNumber()
        carAbout()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun carAbout() {
        CAR_ABOUT = sharedPereferenseHelper.getSmsCode()!!
        binding.apply {
            if (CAR_ABOUT != "") {
                carC.text = "+ ${CAR_ABOUT.substring(1, 3)} C"
                if (CAR_ABOUT.substring(5, 6) == "y") {
                    binding.imgDome.visible()
                } else {
                    binding.imgDome.invisible()
                }
                if (CAR_ABOUT.substring(3, 4) == "y") {
                    imgMainCar.setImageResource(R.drawable.sedan_main)
                } else {
                    imgMainCar.setImageResource(R.drawable.sedan_open_trunk)
                }
                if (CAR_ABOUT.substring(4, 5) == "y") {
                    animMotor.invisible()
                    AppVarebles.MATOROFF = false
                } else {
                    animMotor.visible()
                    AppVarebles.MATOROFF = true
                }
            }
        }
    }
    private fun changePhoneNumber() {
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }


    override fun securityListener(boolean: Boolean) {
        if (boolean)
        {
            sendSms(sharedPereferenseHelper,"*2*")
        }
        else
        {
            sendSms(sharedPereferenseHelper,"*3*")
        }
        vibirator(this)
    }

    override fun motorListener(boolean: Boolean) {
        if (boolean) {
            mediaPlayer(this,R.raw.engine_start)
            sendSms(sharedPereferenseHelper,"*s*")
            sleep(2000)
        } else {
            mediaPlayer(this,R.raw.engine_start)
            sendSms(sharedPereferenseHelper,"*s*")
            sleep(2000)
        }
        vibirator(this)
        //    carAbout()
    }


    override fun carTrunk() {
       // binding.imgMainCar.setImageResource(R.drawable.sedan_open_trunk)
        sendSms(sharedPereferenseHelper,"*4*")
        sleep(1000)
        mediaPlayer(this,R.raw.trunk_open)
    }


    private fun checkSendMessagePerimetion() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS),
                111
            )
        } else
            receiveMsg()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            receiveMsg()
    }

    private fun receiveMsg() {
        var br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, pl: Intent?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    for (sms in Telephony.Sms.Intents.getMessagesFromIntent(pl)) {
                        if (sms.displayMessageBody.contains("&")) {
                            sharedPereferenseHelper.setSmsCode(sms = sms.displayMessageBody)
                        }
                    }
                carAbout()
            }
        }
        registerReceiver(
            br,
            IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        )
    }

    override fun onBackPressed() {
        if (binding.viewPage2.currentItem == 0)
            super.onBackPressed()
        else
            binding.viewPage2.currentItem--
    }


}