package com.example.interview

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.interview.notes.utils.Utils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
          val notificationChannel = NotificationChannel(
              Utils.NOTIFICATION_ID,
              Utils.NOTIFICATION_CHANNEL,
              NotificationManager.IMPORTANCE_HIGH
          )
          val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

          notificationManager.createNotificationChannel(notificationChannel)

      }
    }

}