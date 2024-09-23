package com.example.interview.notes.di

import android.content.Context
import androidx.room.Room
import com.example.interview.notes.data.local.BusDatabase
import com.example.interview.notes.data.repoImpl.BusRepoImpl
import com.example.interview.notes.domain.repository.BusRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BusModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):BusDatabase{
        return Room.databaseBuilder(context,BusDatabase::class.java,"BUS_DB")
            .build()
    }

    @Provides
    @Singleton
    fun provideRepoImpl(busDatabase: BusDatabase):BusRepo{
        return BusRepoImpl(busDatabase.busDao())
    }

}