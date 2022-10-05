package com.example.zamin.smartcarapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.zamin.smartcarapp.adapter.ViewPageAdapter
import com.example.zamin.smartcarapp.databinding.ActivityMainBinding
import com.example.zamin.smartcarapp.fragment.Page1Fragment
import com.example.zamin.smartcarapp.fragment.Page2Fragment

class MainActivity : AppCompatActivity() {
    val items:ArrayList<Fragment> = arrayListOf(Page1Fragment(),Page2Fragment())
    lateinit var adapterFragment:ViewPageAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = Color.parseColor("#0365a6")

        adapterFragment = ViewPageAdapter(items,this)
        binding.viewPage2.adapter = adapterFragment
    }

    override fun onBackPressed() {
        if (binding.viewPage2.currentItem == 0)
        super.onBackPressed()
        else
            binding.viewPage2.currentItem--
    }
}