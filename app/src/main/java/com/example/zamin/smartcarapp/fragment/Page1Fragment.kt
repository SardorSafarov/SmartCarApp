package com.example.zamin.smartcarapp.fragment


import com.example.zamin.smartcarapp.databinding.FragmentPage1Binding


class Page1Fragment(val listener: Security) :
    BaseFragment<FragmentPage1Binding>(FragmentPage1Binding::inflate) {
    var security = true

    interface Security {
        fun securityListener(boolean: Boolean)
    }

    override fun onViewCreate() {

        setOnclicListener()


    }

    private fun setOnclicListener() {
        binding.apply {
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