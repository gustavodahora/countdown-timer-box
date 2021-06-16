package com.example.countdowntimerbox

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.countdowntimerbox.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var endTime = "25/06/2021"

    var formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    var continueTime = false
    var isInit = false
    var initialDay = 0
    var initialMonth = 0
    var initialYear = 0
    var initialHour = 0
    var initialMinut = 0
    var checkedYear = ""
    lateinit var controlDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

    fun onClickDatePicker(v: View?) {

        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_MONTH]
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val hour = calendar[Calendar.HOUR]
        val minute = calendar[Calendar.MINUTE]

        var timerPicker = TimePickerDialog(
            this@MainActivity,
            { view, hour, minute ->

                initialHour = hour
                initialMinut = minute

                var endTime = "$initialDay/${initialMonth}/$initialYear $initialHour:$initialMinut:00"
                controlDate = formatter.parse(endTime)
                binding.date1.setText(endTime)

            },
            hour,
            minute,
            true
        )

        var datePicker = DatePickerDialog(
            this@MainActivity,
            { view, year, month, dayOfMonth ->

                initialDay = dayOfMonth
                initialMonth = month
                initialYear = year

                timerPicker.show()
            },
            year,
            month,
            day
        )

        datePicker.datePicker.minDate = System.currentTimeMillis()
        datePicker.show()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun test(view: View?) {
        var formatter = SimpleDateFormat("HH:mm:ss")

        var cal = Calendar.getInstance()
        cal.set(initialYear, initialMonth, initialDay, initialHour, initialMinut)
        continueTime = true

        var chama: Long = cal.timeInMillis - System.currentTimeMillis()

        val count = object : CountDownTimer(chama, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                var seconds =  (millisUntilFinished / 1000) % 60
                var minutes = ((millisUntilFinished / (1000*60)) % 60)
                var hours =  ((millisUntilFinished / (1000*60*60)) % 24)
                var days =  ((millisUntilFinished / (1000*60*60*24)))

                binding.testesDor.text = "Days: $days -> $hours:$minutes:$seconds"
            }
            override fun onFinish() {
            }
        }.start()
    }

    fun cancelTimer(view: View?) {
        continueTime = false
    }

}