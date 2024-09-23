package com.example.interview.notes.data.repoImpl

import com.example.interview.notes.data.local.BusDao
import com.example.interview.notes.data.local.BusEntity
import com.example.interview.notes.data.mapper.toEntity
import com.example.interview.notes.data.mapper.toModel
import com.example.interview.notes.domain.model.BusModel
import com.example.interview.notes.domain.repository.BusRepo
import kotlinx.coroutines.flow.Flow

class BusRepoImpl (
    private val busDao: BusDao
) : BusRepo {
    override suspend fun insertBus(busModel: BusModel) {
        busDao.insertOrUpdate(busModel.toEntity())
    }

    override suspend fun deleteBus(busModel: BusModel) {
        busDao.deleteItem(busModel.toEntity())
    }

    override suspend fun fetchScreen(id: String): BusModel {
        return busDao.fetchData(id).toModel()
    }

    override fun getAllBus(): Flow<List<BusEntity>> {
        return busDao.getAllData()
    }
}