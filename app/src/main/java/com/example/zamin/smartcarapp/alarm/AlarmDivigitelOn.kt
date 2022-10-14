package com.example.zamin.smartcarapp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import com.example.zamin.smartcarapp.R
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper
import com.example.zamin.smartcarapp.utils.getTimeInMillisNextDay
import com.example.zamin.smartcarapp.utils.mediaPlayer
import com.example.zamin.smartcarapp.utils.sendSms

class AlarmDivigitelOn : BroadcastReceiver() {
    lateinit var alarmManager: AlarmManager
    lateinit var sharedPereferenseHelper: SharedPereferenseHelper
    lateinit var pi:PendingIntent
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.let { mediaPlayer(it, R.raw.engine_start) }
        sharedPereferenseHelper = SharedPereferenseHelper(p0!!.applicationContext)
        sendSms(sharedPereferenseHelper,"*3*")
        val hour = p1!!.getStringExtra("hour")!!.toInt()
        val minute = p1!!.getStringExtra("minute")!!.toInt()
        alarmManager = p0!!.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(p0, AlarmDivigitelOn::class.java)
        intent.putExtra("hour",hour.toString())
        intent.putExtra("minute",minute.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pi = PendingIntent.getBroadcast(
                p0,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            pi = PendingIntent.getBroadcast(
                p0,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTimeInMillisNextDay(hour, minute), pi)
    }
}