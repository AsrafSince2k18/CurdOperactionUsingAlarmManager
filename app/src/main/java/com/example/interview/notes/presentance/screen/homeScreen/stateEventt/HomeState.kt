package com.example.interview.notes.presentance.screen.homeScreen.stateEventt

import com.example.interview.notes.data.local.BusEntity

data class HomeState(

    var name : String="",
    var passenger : String="",
    val setAlarm : Long = System.currentTimeMillis(),
    val busList: List<BusEntity> = emptyList(),
    var busEntity: BusEntity?=null,
    val id:String?=null,
    val oneTime:Boolean=false,
    val repeatTime:Boolean=false,

    )
