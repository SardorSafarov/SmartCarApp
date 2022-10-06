package com.example.zamin.smartcarapp.fragment


import com.example.zamin.smartcarapp.databinding.FragmentPage1Binding


class Page1Fragment(val listener: Security) :
    BaseFragment<FragmentPage1Binding>(FragmentPage1Binding::inflate) {
    var motor = true

    interface Security {
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