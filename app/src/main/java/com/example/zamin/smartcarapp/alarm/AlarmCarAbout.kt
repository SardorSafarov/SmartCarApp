package com.example.zamin.smartcarapp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper
import com.example.zamin.smartcarapp.utils.AppVarebles
import com.example.zamin.smartcarapp.utils.readSms

class AlarmCarAbout: BroadcastReceiver() {
    lateinit var sharedPereferenseHelper: SharedPereferenseHelper
    lateinit var alarmManager: AlarmManager
    lateinit var pi:PendingIntent
    override fun onReceive(p0: Context?, p1: Intent?) {
        sharedPereferenseHelper = SharedPereferenseHelper(p0!!)
        AppVarebles.CAR_ABOUT = readSms(p0!!, sharedPeriferensHelper = sharedPereferenseHelper)
        alarmManager = p0!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(p0, AlarmCarAbout::class.java)
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
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10_000, pi)

    }
}