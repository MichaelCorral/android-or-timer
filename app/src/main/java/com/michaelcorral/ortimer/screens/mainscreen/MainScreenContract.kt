package com.michaelcorral.ortimer.screens.mainscreen

import com.michaelcorral.ortimer.base.BasePresenter
import com.michaelcorral.ortimer.base.BaseView
import com.michaelcorral.ortimer.data.local.TimeEntry

interface MainScreenContract {

    interface View: BaseView {

        fun initializeViews()
        fun displayTimeEntries(timeEntries: List<TimeEntry>)
        fun addTimeEntry(timeEntry: TimeEntry)
        fun startVolumeService()
        fun stopVolumeService()
        fun togglePlayButton()
        fun toggleStopButton()
    }

    interface Presenter : BasePresenter {

        fun onPlayButtonClicked()
        fun saveTimeEntry()
    }
}