package com.michaelcorral.ortimer.screens.mainscreen

import com.michaelcorral.ortimer.base.BasePresenter

interface MainScreenContract {

    interface View {

        fun initializeViews()
        fun startVolumeService()
        fun stopVolumeService()
        fun togglePlayButton()
        fun toggleStopButton()
    }

    interface Presenter : BasePresenter {

        fun onPlayButtonClicked()
    }
}