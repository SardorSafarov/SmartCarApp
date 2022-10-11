package com.example.zamin.smartcarapp.fragment


import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.Toast
import com.example.zamin.smartcarapp.databinding.FragmentPage1Binding


class Page1Fragment(val listener: Page1Interfase) :
    BaseFragment<FragmentPage1Binding>(FragmentPage1Binding::inflate) {
    var motor = true
    var motorOnOff = false

    var motorTime = true
    var alarm = true
    var alarmTime = true

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
                    motor = checkMotorTime()
                    if (motor) {
                        listener.motorListener(motorOnOff)
                        motor = false
                        motorOnOff = !motorOnOff
                    }
                }

                true
            }

            btnCardLockOn.setOnLongClickListener {
                if (checkPhone())
                    alarm = checkAlarm()
                if (alarm) {
                    listener.securityListener(true)
                    alarm = false
                }
                true
            }
            btnCardLockOff.setOnLongClickListener {
                if (checkPhone()) {
                    alarm = checkAlarm()
                    if (alarm) {
                        listener.securityListener(false)
                        alarm = false
                    }
                }

                true
            }
        }
    }

    private fun checkAlarm(): Boolean {
        if (alarmTime) {
            object : CountDownTimer(10_000, 10_000) {
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    alarm = true
                    alarmTime = true
                }
            }.start()
            alarmTime = false
        }
        return alarm
    }

    private fun checkMotorTime(): Boolean {
        if (motorTime) {
            object : CountDownTimer(10_000, 10_000) {
                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    motor = true
                    motorTime = true
                }
            }.start()
            motorTime = false
        }
        return motor
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