package com.gymxy.gymxyone.systemPackages

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.gymxy.gymxyone.R
import com.gymxy.gymxyone.domain.repositoryInterface.AlarmSchedulerInterface
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetNotificationPermission
import com.gymxy.gymxyone.domain.useCases.settingUsecases.GetNotificationTime
import com.gymxy.gymxyone.systemPackages.Constants.CHANNEL_ID
import com.gymxy.gymxyone.systemPackages.Constants.CHANNEL_NAME
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * This class is used to receive the notification from the alarm manager when the alarm is triggered and AlarmReceiver class is used to show the notification.
 */
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    private lateinit var notificationManager: NotificationManager
    private lateinit var alarmSchedulerInterface: AlarmSchedulerInterface
    private lateinit var mediaPlayerManager: MediaPlayerManager
    private lateinit var getNotificationTime: GetNotificationTime
    private lateinit var getNotificationPermission: GetNotificationPermission
    private val TAG = "AlarmReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {

        try {
            // Manually inject dependencies using Hilt
            if (!::mediaPlayerManager.isInitialized) {
                val entryPoint = EntryPointAccessors.fromApplication(
                    context!!.applicationContext,
                    AlarmReceiverEntryPoint::class.java
                )
                notificationManager = entryPoint.notificationManager()
                alarmSchedulerInterface = entryPoint.alarmSchedulerInterface()
                mediaPlayerManager = entryPoint.mediaPlayerManager()
                getNotificationTime = entryPoint.getNotificationTime()
                getNotificationPermission = entryPoint.getNotificationPermission()

            }
            createNotificationChannel()

            val alarmName = intent?.getStringExtra("alarmName")
            CoroutineScope(Dispatchers.Main).launch {
                mediaPlayerManager.play()}

            val stopIntent = Intent(context, AlarmNotificationReceiver::class.java).putExtra("action", "stop")
            val stopPendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    Constants.ALARM_ID,
                    stopIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )


            val notification = NotificationCompat.Builder(context!!, CHANNEL_ID)
                .setContentTitle(Constants.ALARM_NAME)
                .setContentText(Constants.ALARM_MESSAGE)
                .setSmallIcon(R.drawable.ic_launcher_foreground) //TODO change the image
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(
                    R.drawable.baseline_stop_circle_24,
                    "Stop",
                    stopPendingIntent

                ).setDeleteIntent(stopPendingIntent)
                .build()

            notificationManager.notify(Constants.ALARM_ID, notification)

            // after 1 min stop the mediaplayer and schedule the alarm for next day
            CoroutineScope(Dispatchers.IO).launch {
                delay(60000)
                withContext(Dispatchers.Main) {
                    try {
                        mediaPlayerManager.stop()
                        notificationManager.cancel(Constants.ALARM_ID)
                        withContext(Dispatchers.IO) {
                            // schedule next alarm for 24 hours away
                            if(getNotificationPermission.execute()){
                                alarmSchedulerInterface.schedule(getNotificationTime.execute())
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, e.message.toString())
                    }
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = CHANNEL_NAME
        }
        val existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
        if (existingChannel == null) {
            notificationManager.createNotificationChannel(channel)
        }
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(1000, 2000, 3000, 4000, 5000)
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AlarmReceiverEntryPoint {
    fun notificationManager(): NotificationManager
    fun alarmSchedulerInterface(): AlarmSchedulerInterface
    fun mediaPlayerManager(): MediaPlayerManager
    fun getNotificationTime(): GetNotificationTime
    fun getNotificationPermission() : GetNotificationPermission
}