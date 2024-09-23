package com.example.interview.notes.domain.model

data class BusModel(
    val id : Int?=null,
    val busName:String,
    val noPassenger:String,
    val oneTimeAlarm : Boolean=false,
    val repeatTimeAlarm : Boolean=false,
    val time:Long=System.currentTimeMillis(),
)
