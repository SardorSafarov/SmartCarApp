package com.example.zamin.smartcarapp.need

import android.content.Context
import android.util.Log
import android.widget.Toast

fun D(message:String){
    Log.d("sardor", "---->  $message  <----")
}

fun tosatShort(context: Context,text:String){
    Toast.makeText(context, "$text", Toast.LENGTH_SHORT).show()
}
fun tosatLong(context: Context,text:String){
    Toast.makeText(context, "$text", Toast.LENGTH_LONG).show()
}