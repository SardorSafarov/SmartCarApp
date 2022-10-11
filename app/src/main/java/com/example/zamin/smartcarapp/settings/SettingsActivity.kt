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
import com.example.zamin.smartcarapp.alarm.AlarmDivigitelOn
import com.example.zamin.smartcarapp.databinding.ActivitySettingsBinding
import com.example.zamin.smartcarapp.databinding.DialogChangePhoneNumberBinding
import com.example.zamin.smartcarapp.databinding.DialogDivigitelOnTimeBinding
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper
import com.example.zamin.smartcarapp.utils.getTimeInMillis
import com.example.zamin.smartcarapp.utils.getTimeInMillisNextDay


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val sharedPeriferensHelper: SharedPereferenseHelper by lazy {
        SharedPereferenseHelper(this)
    }
    lateinit var alarmManager: AlarmManager
    lateinit var pi: PendingIntent
    var hour: Int = 0
    var minute: Int = 0

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
                btnDivigitelOffTime.text = getDivigitelOffTime()
                swiDivigitelOnTime.isChecked = getSwitchDvigitelOn()
            }

        }


        createDivigitelOnTime()
        dvigitemTimeOnOFF()
    }

    private fun createPhone() {
        binding.btnPhone.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_change_phone_number, null)
            val dialogView = DialogChangePhoneNumberBinding.bind(view)
            alertDialog.setView(view)
            val dialog = alertDialog.create()
            dialog.show()
            dialogView.apply {
                btnDialogOk.setOnClickListener {
                    if (phoneNumber.text!!.isNotEmpty()) {
                        if (phoneNumber.text.toString().length == 9) {
                            sharedPeriferensHelper.setPhone(phoneNumber.text.toString())
                            Toast.makeText(this@SettingsActivity,
                                "Raqam kiritildi!",
                                Toast.LENGTH_SHORT).show()
                        } else
                            Toast.makeText(this@SettingsActivity,
                                "Raqam xato kiritildi!",
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


    private fun createDivigitelOnTime() {
        binding.btnDivigitelOnTime.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_divigitel_on_time, null)
            val dialogView = DialogDivigitelOnTimeBinding.bind(view)
            alertDialog.setView(view)
            alertDialog.setCancelable(false)
            val dialog = alertDialog.create()
            dialog.show()

            dialogView.apply {
                datePicker1.setIs24HourView(true)
                btnDialogOk.setOnClickListener {
                    if (Build.VERSION.SDK_INT >= 23) {
                        hour = datePicker1.getHour()
                        minute = datePicker1.getMinute()
                    } else {
                        hour = datePicker1.getCurrentHour()
                        minute = datePicker1.getCurrentMinute()
                    }
                    saveHourAndMinute()
                    createTimeCheck(getTimeInMillis(hour, minute))
                    dialog.dismiss()
                }
                btnDialogNo.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
    }

    private fun createTimeCheck(timeInMillis: Long) {
        val intent = Intent(this, AlarmDivigitelOn::class.java)
        intent.putExtra("hour", hour.toString())
        intent.putExtra("minute", minute.toString())
        pi = PendingIntent.getBroadcast(this, 0, intent, 0)
        if (timeInMillis > System.currentTimeMillis()) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTimeInMillis(hour, minute), pi)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, getTimeInMillisNextDay(hour, minute), pi)
        }
        binding.swiDivigitelOnTime.isChecked = true
        sharedPeriferensHelper.setSwitchDvigitelOn(true)
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


    private fun dvigitemTimeOnOFF() {
        hour = sharedPeriferensHelper.getDivigitelOnTime()
            .subSequence(0, sharedPeriferensHelper.getDivigitelOnTime().indexOf(":")).toString()
            .toInt()
        minute = sharedPeriferensHelper.getDivigitelOnTime()
            .subSequence(sharedPeriferensHelper.getDivigitelOnTime().indexOf(":") + 1,
                sharedPeriferensHelper.getDivigitelOnTime().length).toString().toInt()
        val intent = Intent(this, AlarmDivigitelOn::class.java)
        intent.putExtra("hour", hour.toString())
        intent.putExtra("minute", minute.toString())
        pi = PendingIntent.getBroadcast(this, 0, intent, 0)
        binding.swiDivigitelOnTime.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            if (b) {
                createTimeCheck(getTimeInMillis(hour.toString().toInt(), minute.toString().toInt()))

            } else {
                alarmManager.cancel(pi)
                sharedPeriferensHelper.setSwitchDvigitelOn(false)
                binding.swiDivigitelOnTime.isChecked = false
            }

        }
    }



    private fun createDivigitelOffTime() {
        binding.btnDivigitelOffTime.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_divigitel_off_time, null)
            val dialogView = DialogChangePhoneNumberBinding.bind(view)
            alertDialog.setView(view)
            val dialog = alertDialog.create()
            dialog.show()
            dialogView.apply {
                btnDialogOk.setOnClickListener {
                    sharedPeriferensHelper.setDivigitelOffTime(phoneNumber.text.toString())
                    binding.btnDivigitelOffTime.text = phoneNumber.text.toString()+" min"
                    dialog.dismiss()
                }
                btnDialogNo.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

    }








}
