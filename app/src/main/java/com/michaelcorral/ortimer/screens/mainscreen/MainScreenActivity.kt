package com.michaelcorral.ortimer.screens.mainscreen

import android.content.Intent
import android.os.Bundle
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.base.BasePresenter
import com.michaelcorral.ortimer.base.OrTimerActivity
import com.michaelcorral.ortimer.services.VolumeService
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

class MainScreenActivity : OrTimerActivity(), MainScreenContract.View {

    private val presenter: MainScreenContract.Presenter by currentScope.inject { parametersOf(this) }

    private lateinit var volumeServiceIntent: Intent
    private lateinit var volumeService: VolumeService

    override fun getActivityLayout(): Int {
        return R.layout.mainscreen_activity
    }

    override fun getActivityPresenter(): BasePresenter {
        return presenter
    }

    override fun onActivityReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onActivityReady(savedInstanceState, intent)

        presenter.setup()
    }

    override fun initializeViews() {
        initializePlayButton()
    }

    private fun initializePlayButton() {
//        mainScreenButtonPlay.setOnClickListener {
            presenter.onPlayButtonClicked()
    }

    override fun startVolumeService() {
//        volumeServiceIntent = Intent(this, VolumeService::class.java)
//        startService(volumeServiceIntent)
//        applicationContext.bindService(volumeServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun togglePlayButton() {
//        mainScreenTextViewStartSession.text = "In Session..."
//        mainScreenButtonPlay.background = getDrawable(R.drawable.all_shape_stop_button)
    }

    override fun stopVolumeService() {
//        stopService(volumeServiceIntent)
    }

    override fun toggleStopButton() {
//        mainScreenTextViewStartSession.text = "Start a Session"
//        mainScreenButtonPlay.background = getDrawable(R.drawable.all_shape_play_button)
    }
}