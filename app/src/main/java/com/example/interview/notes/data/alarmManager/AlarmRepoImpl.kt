package com.example.interview.notes.data.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.interview.notes.data.local.BusEntity
import com.example.interview.notes.utils.Utils
import com.example.interview.notes.utils.Utils.RECEIVE
import com.google.gson.Gson





     fun oneTimeAlarm(context: Context, busEntity: BusEntity) {
        val intent = Intent(context, MyBroadCastReceiver::class.java).apply {
            putExtra(RECEIVE, Gson().toJson(busEntity))
        }
        Log.d("t1", "alarm ${intent.data}")

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            busEntity.time.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                Log.d("t1", "oneTimeAlarm: try catch")

                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    busEntity.time,
                    pendingIntent
                )

            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("t1", "oneTimeAlarm: ${e.printStackTrace()}")
            }
        }
    }

     fun repeatAlarm(context: Context, busEntity: BusEntity) {
        val intent = Intent(context,MyBroadCastReceiver::class.java).apply {
            action=Utils.CANCEL
            putExtra(RECEIVE,Gson().toJson(busEntity))
        }
        val pendingIntent = PendingIntent.getBroadcast(context,
            busEntity.time.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        try {
            val interval = 1L + 60L + 1000
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                busEntity.time,
                interval,
                pendingIntent
            )
        }catch (e:Exception){
            e.printStackTrace()
            Log.d("t1", "repeatAlarm: ${e.printStackTrace()}")
        }
    }

     fun deleteAlarm(context: Context, busEntity: BusEntity) {
        val intent = Intent(context,MyBroadCastReceiver::class.java).apply {
            putExtra(RECEIVE,Gson().toJson(busEntity))
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            busEntity.time.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManage = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManage.cancel(pendingIntent)
    }
