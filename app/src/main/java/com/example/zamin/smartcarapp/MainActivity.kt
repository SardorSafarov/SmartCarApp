package com.example.zamin.smartcarapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        Thread {
            try {
                if (sharedPereferenseHelper.getPhone() != "empty")
                    CAR_ABOUT = readSms(this, sharedPeriferensHelper = sharedPereferenseHelper)
                binding.apply {
                    if (CAR_ABOUT != "") {
                        carC.text = "+ ${CAR_ABOUT.substring(1, 3)} C"
                        if (CAR_ABOUT.substring(3, 4) == "o") {
                            imgMainCar.setImageResource(R.drawable.sedan_open_trunk)
                        }
                        else {
                            imgMainCar.setImageResource(R.drawable.sedan_main)
                        }
                        if (CAR_ABOUT.substring(4, 5) == "y") {
                            animMotor.invisible()
                            AppVarebles.MATOROFF = false
                        }
                        else
                        {
                            animMotor.visible()
                            AppVarebles.MATOROFF = true
                        }
                        if (CAR_ABOUT.substring(5, 6) == "y")
                        {
                            binding.imgDome.visible()
                        }
                        else
                        {
                            binding.imgDome.invisible()
                        }

                    }
                }
            } catch (e: Exception) {
                D(e.message.toString())
            }
            sleep(5_000)
            carAbout()
        }.start()
    }

    private fun changePhoneNumber() {
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }


    override fun securityListener(boolean: Boolean) {
        if (boolean) {
            sendSms(sharedPereferenseHelper,"*2*")
        } else {
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
        carAbout()
    }


    override fun carTrunk() {
       // binding.imgMainCar.setImageResource(R.drawable.sedan_open_trunk)
        sendSms(sharedPereferenseHelper,"*4*")
        sleep(1000)
        mediaPlayer(this,R.raw.trunk_open)
    }


    private fun checkSendMessagePerimetion() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS),
                123)
        } else {
            checkSendMessagePerimetion()
        }
    }

    override fun onBackPressed() {
        if (binding.viewPage2.currentItem == 0)
            super.onBackPressed()
        else
            binding.viewPage2.currentItem--
    }

}