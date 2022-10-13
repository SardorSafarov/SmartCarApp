package com.example.zamin.smartcarapp.fragment


import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.Toast
import com.example.zamin.smartcarapp.databinding.FragmentPage1Binding
import com.example.zamin.smartcarapp.utils.AppVarebles.BTN_CHECK
import com.example.zamin.smartcarapp.utils.D
import com.example.zamin.smartcarapp.utils.checkMotorTime
import com.example.zamin.smartcarapp.utils.tosatShort


class Page1Fragment(val listener: Page1Interfase) :
    BaseFragment<FragmentPage1Binding>(FragmentPage1Binding::inflate) {
    var motorOnOff = false
    interface Page1Interfase {
        fun securityListener(boolean: Boolean)
        fun motorListener(boolean: Boolean)
    }

    override fun onViewCreate() {
        setOnclicListener()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setOnclicListener() {
        binding.apply {

            btnCarMator.setOnLongClickListener {
                if (checkPhone()) {
                    BTN_CHECK = checkMotorTime(requireContext())
                    if (BTN_CHECK) {
                        listener.motorListener(motorOnOff)
                        BTN_CHECK = false
                        motorOnOff = !motorOnOff
                    }
                }
                true
            }

            btnCardLockOn.setOnLongClickListener {
                if (checkPhone())
                    BTN_CHECK = checkMotorTime(requireContext())
                if (BTN_CHECK) {
                    listener.securityListener(true)
                    BTN_CHECK = false
                }
                true
            }
            btnCardLockOff.setOnLongClickListener {
                if (checkPhone()) {
                    BTN_CHECK = checkMotorTime(requireContext())
                    if (BTN_CHECK) {
                        listener.securityListener(false)
                        BTN_CHECK = false
                    }
                }
                true
            }
        }
    }



    private fun checkPhone(): Boolean {
        if (sharedPereferenseHelper.getPhone() == "empty") {
            Toast.makeText(activity, "Ma'lumotlarini kiriting!!", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }
}