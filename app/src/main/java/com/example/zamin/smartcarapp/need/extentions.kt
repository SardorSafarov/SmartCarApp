package com.example.zamin.smartcarapp.need

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper

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

fun mediaPlayer(context: Context,id:Int)
{
    var mediaPlayer = MediaPlayer.create(context, id)
    mediaPlayer.start()
}


fun sendSms(sharedPeriferensHelper: SharedPereferenseHelper,sms:String) {
    val smsManager: SmsManager = SmsManager.getDefault()
    smsManager.sendTextMessage(sharedPeriferensHelper.getPhone(), null, sms, null, null)
}