package com.example.interview.notes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "busEntity")
data class BusEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Int?=null,
    val busName:String,
    val noPassenger:String,
    val oneTimeAlarm : Boolean=false,
    val repeatTimeAlarm : Boolean=false,
    val time:Long=System.currentTimeMillis()

)
