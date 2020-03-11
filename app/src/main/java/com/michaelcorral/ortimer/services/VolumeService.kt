package com.michaelcorral.ortimer.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.VolumeProviderCompat
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class VolumeService : Service(), KoinComponent, VolumeServiceContract.View {

    private lateinit var mediaSessionCompat: MediaSessionCompat

    private val binder: IBinder = LocalBinder()
//    private var listener: VolumeServiceListener? = null

    private val presenter: VolumeServiceContract.Presenter by inject { parametersOf(this) }

    inner class LocalBinder : Binder() {
        val service: VolumeService
            get() = this@VolumeService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()

        presenter.setup()
        Timber.i("onCreate ${this::class.qualifiedName}")
    }

    override fun setupMediaSessionCompat() {
        mediaSessionCompat = MediaSessionCompat(this, this::class.qualifiedName)
        mediaSessionCompat.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 0F)
                .build())

        mediaSessionCompat.setPlaybackToRemote(setupVolumeProvider())
        mediaSessionCompat.isActive = true
    }

    override fun setupVolumeProvider(): VolumeProviderCompat {
        return object : VolumeProviderCompat(
            VOLUME_CONTROL_RELATIVE, 100, 50) {
            override fun onAdjustVolume(direction: Int) {
                if (direction == 0) {
                    presenter.saveTimeEntry()
                }
            }
        }
    }

    //    fun setCallback(listener: VolumeServiceListener) {
//        this.listener = listener
//    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.saveSessionState(false)
        mediaSessionCompat.release()
        Timber.i("onDestroy ${this::class.qualifiedName}")
    }
}