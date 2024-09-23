package com.example.interview.notes.presentance.screen.homeScreen.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interview.notes.data.alarmManager.oneTimeAlarm
import com.example.interview.notes.data.alarmManager.repeatAlarm
import com.example.interview.notes.data.mapper.toEntity
import com.example.interview.notes.data.mapper.toModel
import com.example.interview.notes.domain.model.BusModel
import com.example.interview.notes.domain.repository.BusRepo
import com.example.interview.notes.presentance.screen.homeScreen.stateEventt.HomeEvent
import com.example.interview.notes.presentance.screen.homeScreen.stateEventt.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val busRepo: BusRepo,
    private val application: Application
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    var homeState = _homeState.asStateFlow()

    init {
        getAllData()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Alarm -> {
                _homeState.update {
                    it.copy(setAlarm = event.time)
                }
            }

            is HomeEvent.BusName -> {
                _homeState.update {
                    it.copy(name = event.busName)
                }
            }

            is HomeEvent.Passenger -> {
                _homeState.update {
                    it.copy(passenger = event.passenger)
                }
            }

            HomeEvent.SaveBtn -> {
                insertData()
            }

            HomeEvent.DeleteBtn -> {
                deleteData()
            }

            is HomeEvent.FetchScreen -> {
                _homeState.update {
                    it.copy(id = event.id)
                }
                fetchScreen()
            }

            is HomeEvent.OneTimeAlarm -> {
                _homeState.update {
                    it.copy(oneTime = event.onTime)
                }
            }

            is HomeEvent.RepeatTimeAlarm -> {
                _homeState.update {
                    it.copy(repeatTime = event.repeatTime)
                }
            }
        }
    }


    private fun fetchScreen() {
        viewModelScope.launch {
            try{
                if (homeState.value.id != null) {
                    busRepo.fetchScreen(homeState.value.id!!).apply {
                        _homeState.update {
                            it.copy(
                                name = this.busName,
                                passenger = this.noPassenger,
                                setAlarm = this.time,
                                busEntity = this.toEntity(),
                                oneTime = this.oneTimeAlarm,
                                repeatTime = this.repeatTimeAlarm
                            )
                        }
                    }
                }else{
                    _homeState.update {
                        it.copy(name="",
                            passenger = "",
                            busEntity = null,
                            repeatTime = false,
                            oneTime = false)
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }


    private fun getAllData() {
        viewModelScope.launch {
            busRepo.getAllBus().collect { result ->
                _homeState.update {
                    it.copy(busList = result)
                }
            }
        }
    }


    private fun insertData() {
        viewModelScope.launch {
            try {
                if (homeState.value.busEntity != null) {
                    withContext(Dispatchers.IO) {
                        busRepo.insertBus(
                            BusModel(
                                id = homeState.value.id?.toInt(),
                                busName = homeState.value.name,
                                noPassenger = homeState.value.passenger,
                                time = homeState.value.setAlarm,
                                oneTimeAlarm = homeState.value.oneTime,
                                repeatTimeAlarm = homeState.value.repeatTime
                            )
                        )
                    }
                } else {
                    withContext(Dispatchers.IO) {
                     val data = BusModel(
                                busName = homeState.value.name,
                                noPassenger = homeState.value.passenger,
                                time = homeState.value.setAlarm,
                                oneTimeAlarm = homeState.value.oneTime,
                                repeatTimeAlarm = homeState.value.repeatTime
                            )
                        busRepo.insertBus(busModel = data)
                        if(data.oneTimeAlarm){
                            oneTimeAlarm(application,data.toEntity())
                            Log.d("t1", "insertData: ${data.busName}")
                        }
                        if(data.repeatTimeAlarm){
                            repeatAlarm(application,data.toEntity())
                        }

                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

    private fun deleteData() {
        viewModelScope.launch {
            viewModelScope.launch {
                homeState.value.busEntity?.let { bus ->
                    busRepo.deleteBus(bus.toModel())
                }
            }
        }
    }


}