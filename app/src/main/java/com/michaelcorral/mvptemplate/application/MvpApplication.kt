package com.michaelcorral.mvptemplate.application

import android.app.Application
import com.michaelcorral.mvptemplate.BuildConfig
import com.michaelcorral.mvptemplate.data.itunes.sharedpreferences.SharedPreferencesManager
import com.michaelcorral.mvptemplate.di.modules.itunesDetailsModule
import com.michaelcorral.mvptemplate.di.modules.mainScreenModule
import com.michaelcorral.mvptemplate.di.modules.networkModule
import com.michaelcorral.mvptemplate.di.modules.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MvpApplication : Application() {

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
            androidContext(this@MvpApplication)
            modules(listOf(networkModule, mainScreenModule, repositoryModule, itunesDetailsModule))
        }
    }

    private fun initializeSharedPreferences() {
        SharedPreferencesManager.initialize(this)
    }
}