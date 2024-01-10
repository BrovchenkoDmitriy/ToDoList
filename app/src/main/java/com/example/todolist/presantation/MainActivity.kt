package com.example.todolist.presantation

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import java.util.Locale

class MainActivity : AppCompatActivity() {
//    private lateinit var calendarView: CalendarView
//    private lateinit var timestampStart: TextView
//    private lateinit var timestampEnd: TextView
//
//    private val language = Locale.getDefault()
//    private val formatForDailyWeather = SimpleDateFormat("yyyy, dd MMMM HH:mm", language)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        calendarView = findViewById(R.id.calendarView)
//
//        timestampStart = findViewById(R.id.timestamp_start)
//        timestampEnd = findViewById(R.id.timestamp_end)
//        timestampStart.text = formatForDailyWeather.format(Calendar.getInstance().timeInMillis)
//        calendarView.date = Calendar.getInstance().timeInMillis
//
//
//        calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
//            val calendar = Calendar.getInstance()
//            calendar.set(year, month, dayOfMonth, 0, 0)
//            calendarView.setDate(calendar.timeInMillis, true, true)
//            val timestamp = (calendar.timeInMillis / 100000) * 100000
//            val timestampEndOFDay = timestamp + 86399999
//            val resultStartOfDay = formatForDailyWeather.format(timestamp) + " ($timestamp)"
//            val resultEndOfDay = formatForDailyWeather.format(timestampEndOFDay) + " ($timestampEndOFDay)"
//            timestampStart.text = resultStartOfDay
//            timestampEnd.text = resultEndOfDay
//
//        }
    }
}