package com.example.zamin.smartcarapp.fragment


import android.widget.Toast
import com.example.zamin.smartcarapp.databinding.FragmentPage1Binding


class Page1Fragment(val listener: Page1Interfase) :
    BaseFragment<FragmentPage1Binding>(FragmentPage1Binding::inflate) {
    var motor = false

    interface Page1Interfase {
        fun securityListener(boolean: Boolean)
        fun motorListener(boolean: Boolean)
    }

    override fun onViewCreate() {
        setOnclicListener()
    }

    private fun setOnclicListener() {
        binding.apply {

            btnCarMator.setOnLongClickListener {
                if (checkPhone()){
                    listener.motorListener(motor)
                    motor = !motor
                }

                true
            }

            btnCardLockOn.setOnLongClickListener {
                if (checkPhone())
                listener.securityListener(true)
                true
            }
            btnCardLockOff.setOnLongClickListener {
                if (checkPhone())
                listener.securityListener(false)
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