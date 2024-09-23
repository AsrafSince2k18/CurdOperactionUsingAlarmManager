package com.example.interview.notes.presentance.screen.homeScreen.stateEventt

sealed class HomeEvent {

    data class BusName(val busName : String) : HomeEvent()
    data class Passenger(val passenger : String) : HomeEvent()
    data class Alarm(val time:Long) : HomeEvent()
    data class OneTimeAlarm(val onTime:Boolean) : HomeEvent()
    data class RepeatTimeAlarm(val repeatTime:Boolean) : HomeEvent()
    data class FetchScreen(val id:String?) : HomeEvent()

    data object SaveBtn : HomeEvent()
    data object DeleteBtn : HomeEvent()

}