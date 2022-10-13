package com.example.zamin.smartcarapp.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.zamin.smartcarapp.R
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper
import com.example.zamin.smartcarapp.utils.mediaPlayer
import com.example.zamin.smartcarapp.utils.sendSms

class AlarmDivigitelOff:BroadcastReceiver() {
    lateinit var sharedPereferenseHelper: SharedPereferenseHelper
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.let { mediaPlayer(it, R.raw.engine_stop) }
        sharedPereferenseHelper = SharedPereferenseHelper(p0!!)
        sendSms(sharedPereferenseHelper,"*2*")
    }
}