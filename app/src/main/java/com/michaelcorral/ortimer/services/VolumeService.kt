package com.michaelcorral.ortimer.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.VolumeProviderCompat
import com.michaelcorral.ortimer.data.TimeEntryRepository
import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager
import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager.Key.SessionToggleKey
import com.michaelcorral.ortimer.utils.extensions.currentTime
import org.koin.core.KoinComponent
import org.koin.core.get
import timber.log.Timber
import java.util.*

class VolumeService : Service(), KoinComponent {

    private lateinit var mediaSessionCompat: MediaSessionCompat

    private val binder: IBinder = LocalBinder()
//    private var listener: VolumeServiceListener? = null

    private val repository: TimeEntryRepository = get()

    inner class LocalBinder : Binder() {
        val service: VolumeService
            get() = this@VolumeService
    }

    override fun onCreate() {
        super.onCreate()

        SharedPreferencesManager.put(SessionToggleKey, true)

        mediaSessionCompat = MediaSessionCompat(this, "VolumeService")
        mediaSessionCompat.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(
                    PlaybackStateCompat.STATE_PLAYING,
                    0,
                    0F
                )
                .build()
        )

        val myVolumeProvider = object : VolumeProviderCompat(
            VOLUME_CONTROL_RELATIVE,
            100,
            50
        ) {

            override fun onAdjustVolume(direction: Int) {
                if (direction == 0) {
//                    Timber.d("Service: Time: ${Date().currentTime()}")
                    repository.saveTimeEntry()
                }
            }
        }

        mediaSessionCompat.setPlaybackToRemote(myVolumeProvider)
        mediaSessionCompat.isActive = true

        Timber.i("Volume Service Started")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

//    fun setCallback(listener: VolumeServiceListener) {
//        this.listener = listener
//    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPreferencesManager.put(SessionToggleKey, false)
        mediaSessionCompat.release()
        Timber.i("Service Stopped")
    }
}