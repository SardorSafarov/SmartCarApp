package com.example.zamin.smartcarapp.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zamin.smartcarapp.MainActivity
import com.example.zamin.smartcarapp.R
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper
import com.example.zamin.smartcarapp.login.LoginActivity
import com.example.zamin.smartcarapp.utils.D

class SplashScreenActivity : AppCompatActivity() {
    private val sharedPereferenseHelper:SharedPereferenseHelper by lazy { SharedPereferenseHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        if (sharedPereferenseHelper.getLogin()=="loginn")
        {
            startActivity(Intent(this,MainActivity::class.java))
        }
        else
        {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        finish()
    }
}