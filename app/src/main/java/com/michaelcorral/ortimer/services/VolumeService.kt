package com.michaelcorral.ortimer.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.VolumeProviderCompat
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class VolumeService : Service() {

    private lateinit var mediaSessionCompat: MediaSessionCompat
//    private var savedNotes: Array<Note>? = Gson().fromJson(SharedPreferencesManager.getString(NotesKey), Array<Note>::class.java)
//    private val notes = mutableListOf<Note>()
//    private var notesToBeSaved = mutableListOf<Note>()

    private val binder: IBinder = LocalBinder()
//    private var listener: VolumeServiceListener? = null

//    private val localDataSource = LocalDataSource()

    inner class LocalBinder : Binder() {
        val service: VolumeService
            get() = this@VolumeService
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("Service Started")

//        SharedPreferencesManager.put(SessionToggleKey, true)

        mediaSessionCompat = MediaSessionCompat(this, "VolumeService")
        mediaSessionCompat.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(
                    PlaybackStateCompat.STATE_PLAYING,
                    0,
                    0F
                ) //you simulate a player which plays something.
                .build()
        )

        val myVolumeProvider = object : VolumeProviderCompat(
            VOLUME_CONTROL_RELATIVE, /*max volume*/
            100, /*initial volume level*/
            50
        ) {

            override fun onAdjustVolume(direction: Int) {
                if (direction == 0) {
                    val sdf = SimpleDateFormat("hh:mm:ss")
                    val currentTime = sdf.format(Date())
//                    val note = Note(time = currentTime)
//                    notes.add(note)
//                    notesToBeSaved = ((savedNotes?.toMutableList() ?: mutableListOf()) + notes).toMutableList()
//                    val jsonList = Gson().toJson(notesToBeSaved)
//                    SharedPreferencesManager.put(NotesKey, jsonList)
//                    listener?.updateList()

                    Timber.d("Service: Time: $currentTime")
                }
            }
        }

        mediaSessionCompat.setPlaybackToRemote(myVolumeProvider)
        mediaSessionCompat.isActive = true
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

//    fun setCallback(listener: VolumeServiceListener) {
//        this.listener = listener
//    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("Service Stopped")
//        SharedPreferencesManager.put(SessionToggleKey, false)
        mediaSessionCompat.release()
    }
}