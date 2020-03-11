package com.michaelcorral.ortimer.screens.mainscreen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.base.BasePresenter
import com.michaelcorral.ortimer.base.OrTimerActivity
import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager
import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager.Key.SessionStateKey
import com.michaelcorral.ortimer.services.VolumeService
import kotlinx.android.synthetic.main.mainscreen_activity.*
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

class MainScreenActivity : OrTimerActivity(), MainScreenContract.View {

    private val presenter: MainScreenContract.Presenter by currentScope.inject { parametersOf(this) }

    private var isServiceBounded = false

    private lateinit var volumeServiceIntent: Intent
    private lateinit var volumeService: VolumeService
    private lateinit var serviceConnection: ServiceConnection

    override fun getActivityLayout(): Int {
        return R.layout.mainscreen_activity
    }

    override fun getActivityPresenter(): BasePresenter {
        return presenter
    }

    override fun onActivityReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onActivityReady(savedInstanceState, intent)

        val toggleSession = SharedPreferencesManager.getBoolean(SessionStateKey)
//        presenter.setup(toggleSession)
        presenter.setup()
    }

    override fun initializeViews() {
        initializePlayButton()
    }

    private fun initializePlayButton() {
        mainScreenButtonPlay.setOnClickListener {
            presenter.onPlayButtonClicked()
        }
    }

    override fun startVolumeService() {
        initializeServiceConnection()

        volumeServiceIntent = Intent(this, VolumeService::class.java)
        startService(volumeServiceIntent)
        applicationContext.bindService(volumeServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun initializeServiceConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val localBinder = service as VolumeService.LocalBinder
                volumeService = localBinder.service
                isServiceBounded = true
//                volumeService.setCallback(this@MainActivity)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                stopSession()
            }
        }
    }

    private fun stopSession() {
        if (isServiceBounded) {
            stopService(volumeServiceIntent)
            applicationContext.unbindService(serviceConnection)
            isServiceBounded = false
        }
    }

    override fun togglePlayButton() {
        mainScreenTextViewStartSession.text = getString(R.string.mainscreen_in_session)
        mainScreenButtonPlay.background = getDrawable(R.drawable.all_shape_stop_button)
    }

    override fun stopVolumeService() {
        stopService(volumeServiceIntent)
    }

    override fun toggleStopButton() {
        mainScreenTextViewStartSession.text = getString(R.string.mainscreen_start_a_session)
        mainScreenButtonPlay.background = getDrawable(R.drawable.all_shape_play_button)
    }
}