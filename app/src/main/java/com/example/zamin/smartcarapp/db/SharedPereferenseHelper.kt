package com.example.zamin.smartcarapp.db

import android.content.Context
import android.content.SharedPreferences.Editor

class SharedPereferenseHelper(val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("DB",Context.MODE_PRIVATE)
    private lateinit var editor: Editor
    fun setLogin(string: String){
        editor = sharedPreferences.edit()
        editor.putString("login",string)
        editor.commit()
    }
    fun getLogin() = sharedPreferences.getString("login","empty").toString()
}