package com.example.zamin.smartcarapp.settings

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.zamin.smartcarapp.R
import com.example.zamin.smartcarapp.alarm.AlarmDivigitelOff
import com.example.zamin.smartcarapp.alarm.AlarmDivigitelOn
import com.example.zamin.smartcarapp.databinding.ActivitySettingsBinding
import com.example.zamin.smartcarapp.databinding.DialogChangePhoneNumberBinding
import com.example.zamin.smartcarapp.databinding.DialogDivigitelOffTimeBinding
import com.example.zamin.smartcarapp.databinding.DialogDivigitelOnTimeBinding
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper
import com.example.zamin.smartcarapp.utils.*


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val sharedPeriferensHelper: SharedPereferenseHelper by lazy {
        SharedPereferenseHelper(this)
    }

    lateinit var alarmManager: AlarmManager

    lateinit var pi: PendingIntent

    var hour: Int = 0

    var minute: Int = 0

    var offMinute: Int = 0

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createPhone()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        binding.apply {
            sharedPeriferensHelper.apply {
                btnDivigitelOnTime.text = getDivigitelOnTime()
                btnDivigitelOffTime.text = getDivigitelOffTime() + " min"
                swiDivigitelOnTime.isChecked = getSwitchDvigitelOn()
                swiDivigitelOffTime.isChecked = getSwitchDvigitelOff()
            }
        }
        try {
            divigitelOnTimeDialog()
            switchDvigitemOnTimeOFF()
            divigitelOffTimeDialog()
            switchDvigitemOffTimeOnOff()
        }catch (e:Exception){
            //   D(e.message.toString())
        }
    }

    private fun createPhone() {
        binding.btnPhone.setOnClickListener {
                val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                val view =
                    LayoutInflater.from(this).inflate(R.layout.dialog_change_phone_number, null)
                val dialogView = DialogChangePhoneNumberBinding.bind(view)
                alertDialog.setView(view)
                val dialog = alertDialog.create()
                dialog.show()
                dialogView.apply {
                    btnDialogOk.setOnClickListener {
                        if (phoneNumber.text!!.isNotEmpty()) {
                                sharedPeriferensHelper.setPhone(phoneNumber.text.toString())
                                Toast.makeText(this@SettingsActivity,
                                    "Raqam kiritildi!",
                                    Toast.LENGTH_SHORT).show()
                        } else
                            Toast.makeText(this@SettingsActivity,
                                "Raqam kiritlmadi!",
                                Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    btnDialogNo.setOnClickListener {
                        dialog.dismiss()
                    }
                }
        }
    }

    private fun divigitelOnTimeDialog() {
        binding.btnDivigitelOnTime.setOnClickListener {
            if (checkPhone(this, sharedPeriferensHelper)) {
                val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                val view =
                    LayoutInflater.from(this).inflate(R.layout.dialog_divigitel_on_time, null)
                val dialogView = DialogDivigitelOnTimeBinding.bind(view)
                alertDialog.setView(view)
                alertDialog.setCancelable(false)
                val dialog = alertDialog.create()
                dialog.show()
                dialogView.apply {
                    datePicker1.setIs24HourView(true)
                    btnDialogOk.setOnClickListener {
                        try {
                            if (Build.VERSION.SDK_INT >= 23) {
                                hour = datePicker1.getHour()
                                minute = datePicker1.getMinute()
                            } else {
                                hour = datePicker1.getCurrentHour()
                                minute = datePicker1.getCurrentMinute()
                            }
                            saveHourAndMinute()
                            createDvigitelOnTimeCheck(getTimeInMillis(hour, minute))
                            divigitelOffTime(true)
                        } catch (e: Exception) {
                            Toast.makeText(this@SettingsActivity,
                                "${e.message}",
                                Toast.LENGTH_SHORT).show()
                            binding.erorr.text = "${e.message.toString()}\nErorr:${e.message}"
                        }
                        dialog.dismiss()
                    }
                    btnDialogNo.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }
        }
    }
    private fun saveHourAndMinute() {
        var a = hour.toString()
        var b = minute.toString()
        if (a.length == 1)
            a = "0$a"
        if (b.length == 1)
            b = "0$b"
        binding.btnDivigitelOnTime.text = "$a:$b"
        sharedPeriferensHelper.setDivigitelOnTime("$a:$b")
    }

    @SuppressLint("SuspiciousIndentation")
    private fun switchDvigitemOnTimeOFF() {
        hour = sharedPeriferensHelper.getDivigitelOnTime()
            .subSequence(0, sharedPeriferensHelper.getDivigitelOnTime().indexOf(":")).toString()
            .toInt()
        minute = sharedPeriferensHelper.getDivigitelOnTime()
            .subSequence(sharedPeriferensHelper.getDivigitelOnTime().indexOf(":") + 1,
                sharedPeriferensHelper.getDivigitelOnTime().length).toString().toInt()
        val intent = Intent(this, AlarmDivigitelOn::class.java)
        intent.putExtra("hour", hour.toString())
        intent.putExtra("minute", minute.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pi = PendingIntent.getBroadcast(
                getApplication(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            pi = PendingIntent.getBroadcast(
                getApplication(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        binding.swiDivigitelOnTime.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            if (checkPhone(this, sharedPeriferensHelper)) {
                if (b) {
                    if (sharedPeriferensHelper.getPhone() != "empty") {
                        createDvigitelOnTimeCheck(getTimeInMillis(hour.toString().toInt(),
                            minute.toString().toInt()))
                    }

                } else {
                    alarmManager.cancel(pi)
                    sharedPeriferensHelper.setSwitchDvigitelOn(false)
                    binding.apply {
                        swiDivigitelOnTime.isChecked = false
                        swiDivigitelOffTime.isChecked = false
                    }
                    divigitelOffTime(false)
                }
            }
            else{
                binding.swiDivigitelOnTime.isChecked = false
            }
        }
    }

    private fun createDvigitelOnTimeCheck(timeInMillis: Long) {
        val intent = Intent(this, AlarmDivigitelOn::class.java)
        intent.putExtra("hour", hour.toString())
        intent.putExtra("minute", minute.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pi = PendingIntent.getBroadcast(
                getApplication(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            pi = PendingIntent.getBroadcast(
                getApplication(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        if (timeInMillis > System.currentTimeMillis()) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTimeInMillis(hour, minute), pi)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTimeInMillisNextDay(hour, minute), pi)
        }
        binding.swiDivigitelOnTime.isChecked = true
        sharedPeriferensHelper.setSwitchDvigitelOn(true)
    }

    private fun divigitelOffTimeDialog() {
        binding.btnDivigitelOffTime.setOnClickListener {
            if (checkPhone(this, sharedPeriferensHelper)) {
                val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                val view =
                    LayoutInflater.from(this).inflate(R.layout.dialog_divigitel_off_time, null)
                val dialogView = DialogDivigitelOffTimeBinding.bind(view)
                alertDialog.setView(view)
                val dialog = alertDialog.create()
                dialog.show()
                dialogView.apply {
                    btnDialogOk.setOnClickListener {
                        try {
                            if (minute.text.toString().length == 0) {
                                tosatShort(this@SettingsActivity, "Vaqtni kirting!!")
                            } else {
                                sharedPeriferensHelper.apply {
                                    setDivigitelOffTime(minute.text.toString())
                                    setSwitchDvigitelOff(true)
                                }
                                binding.apply {
                                    btnDivigitelOffTime.text = minute.text.toString() + " min"
                                    swiDivigitelOffTime.isChecked = true
                                }

                                divigitelOffTime(true)
                                dialog.dismiss()
                            }
                        }
                        catch (e:Exception)
                        {
                            Toast.makeText(this@SettingsActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                        }
                       
                    }
                    btnDialogNo.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }
        }

    }

    private fun switchDvigitemOffTimeOnOff() {
        binding.swiDivigitelOffTime.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            if (checkPhone(this, sharedPeriferensHelper)) {
                if (b) {
                    if (sharedPeriferensHelper.getPhone() != "empty")
                        divigitelOffTime(true)
                } else {
                    divigitelOffTime(false)
                }
            }
            else{
                binding.swiDivigitelOffTime.isChecked = false
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun divigitelOffTime(b: Boolean) {
        D(b.toString())
        offMinute = sharedPeriferensHelper.getDivigitelOffTime().toInt()
        getTimeInMillis(hour, minute) + offMinute * 10_000L
        val intent = Intent(this, AlarmDivigitelOff::class.java)
        intent.putExtra("hour", hour.toString())
        intent.putExtra("minute", minute.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pi = PendingIntent.getBroadcast(
                getApplication(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            pi = PendingIntent.getBroadcast(
                getApplication(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        if (b) {
            if (sharedPeriferensHelper.getPhone()!="empty")
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTimeInMillis(hour, minute) + offMinute * 60_000L, pi)
            sharedPeriferensHelper.setSwitchDvigitelOff(true)
        } else {
            alarmManager.cancel(pi)
            sharedPeriferensHelper.setSwitchDvigitelOff(false)
        }
    }
}
