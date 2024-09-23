package com.example.interview.notes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BusEntity::class], version = 1, exportSchema = false)
abstract class BusDatabase : RoomDatabase(){
    abstract fun busDao() : BusDao
}