package com.example.zamin.smartcarapp.fragment


import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.zamin.smartcarapp.databinding.FragmentPage1Binding
import com.example.zamin.smartcarapp.utils.AppVarebles.BTN_CHECK
import com.example.zamin.smartcarapp.utils.AppVarebles.MATOROFF
import com.example.zamin.smartcarapp.utils.checkMotorTime
import com.example.zamin.smartcarapp.utils.checkPhone


class Page1Fragment(val listener: Page1Interfase) :
    BaseFragment<FragmentPage1Binding>(FragmentPage1Binding::inflate) {
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
                if (checkPhone(requireContext(),sharedPereferenseHelper)) {
                    BTN_CHECK = checkMotorTime(requireContext())
                    if (BTN_CHECK) {
                        listener.motorListener(MATOROFF)
                        BTN_CHECK = false
                        MATOROFF = !MATOROFF
                    }
                }
                true
            }
            btnCardLockOn.setOnLongClickListener {
                if (checkPhone(requireContext(),sharedPereferenseHelper))
                    BTN_CHECK = checkMotorTime(requireContext())
                if (BTN_CHECK) {
                    listener.securityListener(true)
                    BTN_CHECK = false
                }
                true
            }
            btnCardLockOff.setOnLongClickListener {
                if (checkPhone(requireContext(),sharedPereferenseHelper)) {
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




}