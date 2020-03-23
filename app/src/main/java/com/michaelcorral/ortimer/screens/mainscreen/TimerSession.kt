package com.michaelcorral.ortimer.screens.mainscreen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.michaelcorral.ortimer.services.VolumeService
import com.michaelcorral.ortimer.services.VolumeServiceListener

class TimerSession(
    private val context: Context,
    private val listener: VolumeServiceListener
) {

    private var isServiceBounded = false

    private lateinit var volumeServiceIntent: Intent
    private lateinit var volumeService: VolumeService
    private lateinit var serviceConnection: ServiceConnection

    fun start() {
        initializeServiceConnection()
        initializeVolumeService()
    }

    private fun initializeServiceConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val localBinder = service as VolumeService.LocalBinder
                volumeService = localBinder.service
                isServiceBounded = true
                volumeService.setCallback(listener)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                stop()
            }
        }
    }

    private fun initializeVolumeService() {
        volumeServiceIntent = Intent(context, VolumeService::class.java)
        context.startService(volumeServiceIntent)
        context.applicationContext.bindService(
            volumeServiceIntent,
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    fun stop() {
        if (isServiceBounded) {
            context.stopService(volumeServiceIntent)
            context.applicationContext.unbindService(serviceConnection)
            isServiceBounded = false
        }
    }
}