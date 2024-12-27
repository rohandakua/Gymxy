package com.gymxy.gymxyone.systemPackages

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.gymxy.gymxyone.domain.helperFunctions.getExactTime
import com.gymxy.gymxyone.domain.models.Result
import com.gymxy.gymxyone.domain.repositoryInterface.AlarmSchedulerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmSchedulerInterfaceImplementation @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
) : AlarmSchedulerInterface {
    private val TAG = "AlarmSchedulerInterfaceImplementation"

    override suspend fun schedule(time: Long): Result {
        withContext(Dispatchers.Default) {
            val intent = Intent(context, AlarmReceiver::class.java).putExtra(
                "alarmId",
                Constants.ALARM_ID
            ).putExtra("alarmName", Constants.ALARM_NAME)
            val alarmIntent = PendingIntent.getBroadcast(
                context,
                Constants.ALARM_ID,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            try {
                if (Build.VERSION.SDK_INT > 30 && !alarmManager.canScheduleExactAlarms()) {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(
                            context,
                            "Please allow the app to schedule exact alarms",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return@withContext Result.FAILURE
                }
                alarmManager.setAlarmClock(
                    AlarmManager.AlarmClockInfo(
                        getExactTime(time),
                        alarmIntent
                    ),
                    alarmIntent
                )
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
        return Result.SUCCESS
    }

    override suspend fun cancel(time: Long): Result {
        try {
            val intent = Intent(context, AlarmReceiver::class.java).putExtra(
                "alarmId",
                Constants.ALARM_ID
            ).putExtra("alarmName", Constants.ALARM_NAME)
            val alarmIntent = PendingIntent.getBroadcast(
                context,
                Constants.ALARM_ID,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            try {
                if (alarmIntent != null) {
                    alarmManager.cancel(alarmIntent)
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                return Result.FAILURE
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            return Result.FAILURE
        }
        return Result.SUCCESS
    }
}