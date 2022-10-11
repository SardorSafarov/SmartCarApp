package com.example.zamin.smartcarapp.db

import android.content.Context
import android.content.SharedPreferences.Editor

class SharedPereferenseHelper(val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("DB", Context.MODE_PRIVATE)
    private lateinit var editor: Editor

    fun setLogin(string: String) {
        editor = sharedPreferences.edit()
        editor.putString("login", string)
        editor.commit()
    }

    fun getLogin() = sharedPreferences.getString("login", "empty").toString()


     fun setPhone(string: String) {
        editor = sharedPreferences.edit()
        editor.putString("phone", string)
        editor.commit()
    }

    fun getPhone() = sharedPreferences.getString("phone", "empty").toString()

     fun setDivigitelOnTime(string: String) {
        editor = sharedPreferences.edit()
        editor.putString("time",string)
        editor.commit()
    }
    fun getDivigitelOnTime() = sharedPreferences.getString("time","00:00").toString()

    fun setDivigitelOffTime(string: String){
        editor = sharedPreferences.edit()
        editor.putString("divigitelTime",string)
        editor.commit()
    }
    fun getDivigitelOffTime() = sharedPreferences.getString("divigitelTime","0").toString()


    fun setSwitchDvigitelOn(bool: Boolean) {
        editor = sharedPreferences.edit()
        editor.putBoolean("dvigitelOnSwitch", bool)
        editor.commit()
    }

    fun getSwitchDvigitelOn() = sharedPreferences.getBoolean("dvigitelOnSwitch", false)

    fun setSwitchDvigitelOff(bool: Boolean) {
        editor = sharedPreferences.edit()
        editor.putBoolean("dvigitelOffSwitch", bool)
        editor.commit()
    }

    fun getSwitchDvigitelOff() = sharedPreferences.getBoolean("dvigitelOffSwitch", false)





}