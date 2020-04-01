package com.michaelcorral.ortimer.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.media.VolumeProviderCompat
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.data.local.TimeEntry
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class VolumeService : Service(), KoinComponent, VolumeServiceContract.View {

    private lateinit var mediaSessionCompat: MediaSessionCompat

    private val binder: IBinder = LocalBinder()
    private var listener: VolumeServiceListener? = null

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

    //TODO: REFACTOR FOR BETTER IMPLEMENTATION
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == START) {

            //Stopping a service from the notification
            val stopIntent = Intent(this, VolumeService::class.java)
            stopIntent.action = STOP
            val stopSessionIntent = PendingIntent.getService(this, 1, stopIntent, 0)

            setupMediaSessionCompat()

            val collapsedView = RemoteViews(packageName, R.layout.notification_layout_collapsed)

            collapsedView.setOnClickPendingIntent(R.id.notificationLayoutLinearLayoutEndSessionContainer, stopSessionIntent)

            val notification = NotificationCompat.Builder(this, getString(R.string.ortimerapplication_notification_channel_id))
                .setSmallIcon(R.drawable.all_image_timer_white_96dp)
                .setCustomContentView(collapsedView)
                .build()

            startForeground(1, notification)
        }

        else if (intent?.action == STOP) {
            listener?.toggleStopButton()
            listener?.toggleAddButton(false)
            presenter.saveSessionState(false)
            mediaSessionCompat.release()
            presenter.detachView()
            listener = null
            stopForeground(true)
            stopSelf()
        }

        return START_STICKY
    }

    override fun setupMediaSessionCompat() {
        mediaSessionCompat = MediaSessionCompat(this, this::class.qualifiedName)
        mediaSessionCompat.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 0F)
                .build()
        )

        mediaSessionCompat.setPlaybackToRemote(setupVolumeProvider())
        mediaSessionCompat.isActive = true
    }

    override fun setupVolumeProvider(): VolumeProviderCompat {
        return object : VolumeProviderCompat(
            VOLUME_CONTROL_RELATIVE, 100, 50
        ) {
            override fun onAdjustVolume(direction: Int) {
                if (direction == 0) {
                    presenter.saveTimeEntry()
                }
            }
        }
    }

    fun setCallback(listener: VolumeServiceListener) {
        this.listener = listener
    }

    override fun addTimeEntry(timeEntry: TimeEntry) {
        listener?.displayTimeEntryInList(timeEntry)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.saveSessionState(false)
        mediaSessionCompat.release()
        presenter.detachView()
        listener = null
        Timber.i("onDestroy ${this::class.qualifiedName}")
    }

    companion object {
        const val START = "start"
        const val STOP = "stop"
    }
}