package com.example.zamin.smartcarapp.fragment

import com.example.zamin.smartcarapp.databinding.FragmentPage2Binding

class Page2Fragment(val listener: Page2Interface) :
    BaseFragment<FragmentPage2Binding>(FragmentPage2Binding::inflate) {
    interface Page2Interface {
        fun carTrunk()
    }

    override fun onViewCreate() {

        btnSetOnClickListener()
    }

    private fun btnSetOnClickListener() {
        binding.apply {
            btnCarTrunk.setOnLongClickListener {
                listener.carTrunk()
                true
            }
        }
    }


}