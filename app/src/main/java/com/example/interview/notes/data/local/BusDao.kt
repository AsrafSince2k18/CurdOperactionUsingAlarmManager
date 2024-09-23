package com.example.interview.notes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BusDao {

    @Upsert
    suspend fun insertOrUpdate(busEntity: BusEntity)

    @Delete
    suspend fun deleteItem(busEntity: BusEntity)

    @Query("SELECT * FROM busEntity WHERE time ORDER BY time DESC")
    fun getAllData():Flow<List<BusEntity>>

    @Query("SELECT * FROM busEntity WHERE id LIKE(:id)")
    suspend fun fetchData(id:String) : BusEntity

}