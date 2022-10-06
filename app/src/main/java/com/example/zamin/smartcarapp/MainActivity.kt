package com.example.zamin.smartcarapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.zamin.smartcarapp.adapter.ViewPageAdapter
import com.example.zamin.smartcarapp.databinding.ActivityMainBinding
import com.example.zamin.smartcarapp.databinding.DialogChangePhoneNumberBinding
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper
import com.example.zamin.smartcarapp.fragment.Page1Fragment
import com.example.zamin.smartcarapp.fragment.Page2Fragment
import com.example.zamin.smartcarapp.need.invisible
import com.example.zamin.smartcarapp.need.mediaPlayer
import com.example.zamin.smartcarapp.need.vibirator
import com.example.zamin.smartcarapp.need.visible
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity(), Page1Fragment.Page1Interfase,
    Page2Fragment.Page2Interface {


    private lateinit var binding: ActivityMainBinding
    val items: ArrayList<Fragment> = arrayListOf(Page1Fragment(this), Page2Fragment(this))
    lateinit var adapterFragment: ViewPageAdapter
    private val sharedPeriferensHelper: SharedPereferenseHelper by lazy {
        SharedPereferenseHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.parseColor("#075d99")
        adapterFragment = ViewPageAdapter(items, this)
        binding.viewPage2.adapter = adapterFragment
        binding.indicator.setViewPager(binding.viewPage2)
        changePhoneNumber()
    }

    private fun changePhoneNumber() {
        binding.changePhoneNumber.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_change_phone_number, null)
            val dialogView = DialogChangePhoneNumberBinding.bind(view)
            alertDialog.setView(view)
            val dialog = alertDialog.create()
            dialog.show()
            dialogView.apply {
                btnDialogOk.setOnClickListener {
                    if (phoneNumber.text!!.isNotEmpty()) {
                        if (phoneNumber.text.toString().length == 9) {
                            sharedPeriferensHelper.setPhone(phoneNumber.text.toString())
                            Toast.makeText(this@MainActivity,
                                "Raqam kiritildi!",
                                Toast.LENGTH_SHORT).show()
                        } else
                            Toast.makeText(this@MainActivity,
                                "Raqam xato kiritildi!",
                                Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(this@MainActivity,
                            "Raqam kiritlmadi!",
                            Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                btnDialogNo.setOnClickListener {
                    dialog.dismiss()
                }
            }


        }
    }


    override fun securityListener(boolean: Boolean) {
        if (boolean) {
            binding.imgDome.invisible()
        } else {
            binding.imgDome.visible()
        }
        vibirator(this)
    }

    override fun motorListener(boolean: Boolean) {
        if (boolean) {
            mediaPlayer(this,R.raw.engine_stop)
            sleep(2000)
            binding.animMotor.invisible()
        } else {
            mediaPlayer(this,R.raw.engine_start)
            sleep(2000)
            binding.animMotor.visible()

        }
        vibirator(this)
    }

    override fun onBackPressed() {
        if (binding.viewPage2.currentItem == 0)
            super.onBackPressed()
        else
            binding.viewPage2.currentItem--
    }

    override fun carTrunk() {
        binding.imgMainCar.setImageResource(R.drawable.sedan_open_trunk)
        sleep(1000)
        mediaPlayer(this,R.raw.trunk_open)
    }
}