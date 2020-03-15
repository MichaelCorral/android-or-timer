package com.michaelcorral.ortimer.application

import android.app.Application
import com.michaelcorral.ortimer.BuildConfig
import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager
import com.michaelcorral.ortimer.di.modules.mainScreenModule
import com.michaelcorral.ortimer.di.modules.repositoryModule
import com.michaelcorral.ortimer.di.modules.volumeServiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class OrTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
        initializeKoin()
        initializeSharedPreferences()
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
            modules(listOf(mainScreenModule, repositoryModule, volumeServiceModule))
        }
    }

    private fun initializeSharedPreferences() {
        SharedPreferencesManager.initialize(this)
    }
}