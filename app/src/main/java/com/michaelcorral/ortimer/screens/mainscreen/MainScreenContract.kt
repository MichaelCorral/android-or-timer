package com.michaelcorral.ortimer.screens.mainscreen

import com.michaelcorral.ortimer.base.BasePresenter
import com.michaelcorral.ortimer.data.local.TimeEntry

interface MainScreenContract {

    interface View {

        fun initializeViews()
        fun displayTimeEntries(timeEntries: List<TimeEntry>)
        fun startVolumeService()
        fun stopVolumeService()
        fun togglePlayButton()
        fun toggleStopButton()
    }

    interface Presenter : BasePresenter {

        fun onPlayButtonClicked()
    }
}