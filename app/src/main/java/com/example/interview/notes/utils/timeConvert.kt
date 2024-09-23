package com.example.interview.notes.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun timeConvert(time:Long) : String{
    val sdf= SimpleDateFormat("dd:MM:yyyy hh:mm a", Locale.getDefault())
    val date = Date(time)
    return sdf.format(date)
}