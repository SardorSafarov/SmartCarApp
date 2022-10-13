package com.example.zamin.smartcarapp.fragment

import android.annotation.SuppressLint
import android.widget.Toast
import com.example.zamin.smartcarapp.R
import com.example.zamin.smartcarapp.databinding.FragmentPage2Binding
import com.example.zamin.smartcarapp.utils.AppVarebles.BTN_CHECK
import com.example.zamin.smartcarapp.utils.checkMotorTime
import com.example.zamin.smartcarapp.utils.mediaPlayer
import com.example.zamin.smartcarapp.utils.sendSms
import com.example.zamin.smartcarapp.utils.vibirator

class Page2Fragment(val listener: Page2Interface) :
    BaseFragment<FragmentPage2Binding>(FragmentPage2Binding::inflate) {
    interface Page2Interface {
        fun carTrunk()
    }

    override fun onViewCreate() {
        btnSetOnClickListener()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun btnSetOnClickListener() {
        binding.apply {
            btnCarTrunk.setOnLongClickListener {
                if (checkPhone()) {
                    BTN_CHECK = checkMotorTime(requireContext())
                    if (BTN_CHECK)
                    {
                        listener.carTrunk()
                        BTN_CHECK = false
                    }
                }
                true
            }
            btnCarMatorOff.setOnLongClickListener {
                BTN_CHECK = checkMotorTime(requireContext())
                if (BTN_CHECK){
                    sendSms(sharedPereferenseHelper,"*0*")
                    mediaPlayer(requireContext(), R.raw.engine_stop)
                    vibirator(requireContext())
                    BTN_CHECK = false
                }
                true
            }

            btnCarBibBib.setOnLongClickListener {

                if (checkPhone()) {
                    BTN_CHECK = checkMotorTime(requireContext())
                    if (BTN_CHECK){
                        mediaPlayer(requireContext(),R.raw.horn)
                        sendSms(sharedPereferenseHelper,"*5*")
                        vibirator(requireContext())
                        BTN_CHECK = false
                    }
                }
                true
            }
        }
    }

    private fun checkPhone():Boolean {
        if(sharedPereferenseHelper.getPhone()=="empty")
        {
            Toast.makeText(activity, "Ma'lumotlarini kiriting!!", Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }
    }
}