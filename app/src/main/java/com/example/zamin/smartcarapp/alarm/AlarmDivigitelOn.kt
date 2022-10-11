package com.example.zamin.smartcarapp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import com.example.zamin.smartcarapp.R
import com.example.zamin.smartcarapp.utils.getTimeInMillisNextDay
import com.example.zamin.smartcarapp.utils.mediaPlayer

class AlarmDivigitelOn : BroadcastReceiver() {
    lateinit var alarmManager: AlarmManager
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.let { mediaPlayer(it, R.raw.engine_start) }
        val hour = p1!!.getStringExtra("hour")!!.toInt()
        val minute = p1!!.getStringExtra("minute")!!.toInt()
        alarmManager = p0!!.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(p0, AlarmDivigitelOn::class.java)
        intent.putExtra("hour",hour.toString())
        intent.putExtra("minute",minute.toString())
        val pi = PendingIntent.getBroadcast(p0, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTimeInMillisNextDay(hour, minute), pi)
    }
}