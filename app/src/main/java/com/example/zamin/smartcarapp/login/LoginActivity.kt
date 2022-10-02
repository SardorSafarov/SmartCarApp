package com.example.zamin.smartcarapp.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zamin.smartcarapp.MainActivity
import com.example.zamin.smartcarapp.R
import com.example.zamin.smartcarapp.databinding.ActivityLoginBinding
import com.example.zamin.smartcarapp.need.tosatLong

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = R.color.white
        binding.btnLogin.setOnClickListener {
            if (binding.edtLogin.text.toString()=="Smartcar" && binding.edtPassword.text.toString()=="112233")
            {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }else
            {
                tosatLong(context = this,"Login parol xato!")
            }
        }
    }
}