package com.example.zamin.smartcarapp.login

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zamin.smartcarapp.MainActivity
import com.example.zamin.smartcarapp.R
import com.example.zamin.smartcarapp.databinding.ActivityLoginBinding
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper
import com.example.zamin.smartcarapp.need.tosatLong

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val sharedPereferenseHelper:SharedPereferenseHelper by lazy { SharedPereferenseHelper(this) }
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = R.color.white
        binding.btnLogin.setOnClickListener {
            if (binding.edtLogin.text.toString().trim() == "Smartcar" && binding.edtPassword.text.toString().trim() == "112233") {
                startActivity(Intent(this,MainActivity::class.java))
                sharedPereferenseHelper.setLogin("login")
                finish()
            } else {
                tosatLong(context = this, "Login parol xato!")
            }
        }
    }
}