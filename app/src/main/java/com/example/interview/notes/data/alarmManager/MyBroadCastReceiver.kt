package com.example.interview.notes.data.alarmManager

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.interview.R
import com.example.interview.notes.data.local.BusEntity
import com.example.interview.notes.utils.Utils
import com.example.interview.notes.utils.Utils.RECEIVE
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

class MyBroadCastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent?) {


        val receive = intent?.getStringExtra(RECEIVE) ?: return

        val entity = Gson().fromJson(receive, BusEntity::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            entity.time.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        when(intent.action){
            Utils.CANCEL -> {
                runBlocking {
                    deleteAlarm(context,entity)
                }
            }
        }



        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED
        ) {

            val notification = NotificationCompat.Builder(context, Utils.NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("My bus")
                .setContentText(entity.busName)
                .addAction(R.drawable.ic_launcher_foreground, "cancel", pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build()

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)

            wakeUpScreenLight(context)



        }


    }
}


private fun wakeUpScreenLight(context: Context) {

    val light = context.getSystemService(Context.POWER_SERVICE) as PowerManager

    val wakeUp = light.newWakeLock(
        PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "myapp:mywakelocktag"
    )
    wakeUp.acquire(3000)

}