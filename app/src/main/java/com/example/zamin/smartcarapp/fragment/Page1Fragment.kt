package com.example.zamin.smartcarapp.fragment


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
                listener.motorListener(motor)
                motor = !motor
                true
            }

            btnCardLockOn.setOnLongClickListener {
                listener.securityListener(true)
                true
            }
            btnCardLockOff.setOnLongClickListener {
                listener.securityListener(false)
                true
            }
        }
    }


}