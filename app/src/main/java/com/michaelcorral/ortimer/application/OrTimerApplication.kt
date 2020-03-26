package com.michaelcorral.ortimer.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.michaelcorral.ortimer.BuildConfig
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager
import com.michaelcorral.ortimer.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.*

class OrTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
        initializeKoin()
        initializeSharedPreferences()
        initializeNotificationChannel()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeKoin() {
        startKoin {
            androidLogger()
            androidContext(this@OrTimerApplication)
            modules(
                listOf(
                    mainScreenModule,
                    repositoryModule,
                    volumeServiceModule,
                    settingsModule,
                    aboutModule
                )
            )
        }
    }

    private fun initializeSharedPreferences() {
        SharedPreferencesManager.initialize(this)
    }

    private fun initializeNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val description = getString(R.string.ortimerapplication_notification_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.ortimerapplication_notification_channel_id), name, importance)
            channel.description = description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}