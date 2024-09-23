package com.example.interview.notes.domain.repository

import com.example.interview.notes.data.local.BusEntity
import com.example.interview.notes.domain.model.BusModel
import kotlinx.coroutines.flow.Flow

interface BusRepo {
    suspend fun insertBus(busModel: BusModel)
    suspend fun deleteBus(busModel: BusModel)
    suspend fun fetchScreen(id:String):BusModel
    fun getAllBus():Flow<List<BusEntity>>
}