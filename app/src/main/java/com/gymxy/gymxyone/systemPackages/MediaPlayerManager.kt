package com.gymxy.gymxyone.systemPackages

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @class MediaPlayerManager
 * it is used to provide a media player for the alarm that is to be set
 * it is a singleton class so that only one instance of the media player is created
 * it provides a alarm type media player
 */
@Singleton
class MediaPlayerManager @Inject constructor(
    private val context: Context
) {
    private val TAG = "MediaPlayerManager"
    private var mediaPlayer: MediaPlayer? = null
    fun play() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
            )
            setDataSource(context,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            prepare()
        }
        mediaPlayer?.start()

    }
    fun stop() {
        mediaPlayer?.apply {
            if(isPlaying){
                stop()
            }
            release()
        }
        mediaPlayer=null
    }

}