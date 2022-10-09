package com.example.zamin.smartcarapp.settings

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.zamin.smartcarapp.R
import com.example.zamin.smartcarapp.databinding.ActivitySettingsBinding
import com.example.zamin.smartcarapp.databinding.DialogChangePhoneNumberBinding
import com.example.zamin.smartcarapp.databinding.DialogTimePickerBinding
import com.example.zamin.smartcarapp.db.SharedPereferenseHelper


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val sharedPeriferensHelper: SharedPereferenseHelper by lazy {
        SharedPereferenseHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnDivigitelOnTime.text = sharedPeriferensHelper.getDivigitelOnTime()
        binding.btnDivigitelOffTime.text = sharedPeriferensHelper.getDivigitelOffTime()
        createPhone()
        createDivigitelOnTime()
        createDivigitelOffTime()


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

    private fun createDivigitelOnTime() {
        binding.btnDivigitelOnTime.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_time_picker, null)
            val dialogView = DialogTimePickerBinding.bind(view)
            alertDialog.setView(view)
            val dialog = alertDialog.create()
            dialog.show()

            dialogView.apply {
                datePicker1.setIs24HourView(true)
                btnDialogOk.setOnClickListener {
                    var hour: String
                    var minute: String
                    if (Build.VERSION.SDK_INT >= 23) {
                        hour = datePicker1.getHour().toString()
                        minute = datePicker1.getMinute().toString()
                    } else {
                        hour = datePicker1.getCurrentHour().toString()
                        minute = datePicker1.getCurrentMinute().toString()
                    }
                    if (minute.length == 1)
                        minute = "0$minute"
                    if (hour.length == 1)
                        hour = "0$hour"
                    binding.btnDivigitelOnTime.text = "$hour:$minute"
                    sharedPeriferensHelper.setDivigitelOnTime("$hour:$minute")
                    dialog.dismiss()
                }
                btnDialogNo.setOnClickListener {
                    dialog.dismiss()
                }


            }
        }
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
}