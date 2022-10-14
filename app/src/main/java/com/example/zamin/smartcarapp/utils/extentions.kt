package com.example.zamin.smartcarapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Telephony
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper
import java.util.*

fun D(message:String){
    Log.d("sardor", "---->  $message  <----")
}

fun tosatShort(context: Context,text:String){
    Toast.makeText(context, "$text", Toast.LENGTH_SHORT).show()
}

fun tosatLong(context: Context, text: String) {
    Toast.makeText(context, "$text", Toast.LENGTH_LONG).show()
}

fun View.visible(): View {
    visibility = View.VISIBLE
    return this
}

fun View.invisible():View{
    visibility = View.INVISIBLE
    return this
}

fun vibirator(applicationContext: Context) {
    val vibrator = applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(500)
    }
}

fun mediaPlayer(context: Context, id: Int) {
    var mediaPlayer = MediaPlayer.create(context, id)
    mediaPlayer.start()
}


fun sendSms(sharedPeriferensHelper: SharedPereferenseHelper, sms: String) {
    val smsManager: SmsManager = SmsManager.getDefault()
    smsManager.sendTextMessage(sharedPeriferensHelper.getPhone(), null, sms, null, null)
}

@SuppressLint("Range")
fun readSms(
    context: Context,
    sharedPeriferensHelper: SharedPereferenseHelper,
): String {
    val numberCol = Telephony.TextBasedSmsColumns.ADDRESS
    val textCol = Telephony.TextBasedSmsColumns.BODY
    val typeCol = Telephony.TextBasedSmsColumns.TYPE

    val projection = arrayOf(numberCol, textCol, typeCol)

    val cursor = context.contentResolver.query(
        Telephony.Sms.CONTENT_URI,
        projection, null, null, null
    )

    val numberColIdx = cursor!!.getColumnIndex(numberCol)
    val textColIdx = cursor.getColumnIndex(textCol)
    val typeColIdx = cursor.getColumnIndex(typeCol)

    while (cursor.moveToNext()) {
        val number = cursor.getString(numberColIdx)
        val text = cursor.getString(textColIdx)
        val type = cursor.getString(typeColIdx)
       try {
          if (number.contains(sharedPeriferensHelper.getPhone()))
          {
            if (text.contains("&"))
            {
                D(text)
                return text
            }
          }
       }catch (e:Exception){
           D(e.toString())
       }
    }
    cursor.close()
    return ""
}

fun getTimeInMillis(hour: Int, minute: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.timeInMillis
}

fun getTimeInMillisNextDay(hour: Int, minute: Int):Long {
    val calendar = Calendar.getInstance()
    calendar.apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.DAY_OF_WEEK,Calendar.DAY_OF_WEEK%7+1)
        set(Calendar.SECOND,0)
        set(Calendar.MILLISECOND,0)
    }
    return calendar.timeInMillis
}


fun checkMotorTime(context: Context): Boolean {
    if (AppVarebles.BTN_CHECK) {
        object : CountDownTimer(10_000, 10_000) {
            override fun onTick(p0: Long) {

            }
            override fun onFinish() {
                AppVarebles.BTN_CHECK = true
            }
        }.start()
    }
    else{
        tosatShort(context,"Sabir")
    }
    return AppVarebles.BTN_CHECK
}

fun checkPhone(context: Context,sharedPereferenseHelper:SharedPereferenseHelper): Boolean {
    if (sharedPereferenseHelper.getPhone() == "empty") {
        Toast.makeText(context, "Ma'lumotlarini kiriting!!", Toast.LENGTH_SHORT).show()
        return false
    } else {
        return true
    }
}
