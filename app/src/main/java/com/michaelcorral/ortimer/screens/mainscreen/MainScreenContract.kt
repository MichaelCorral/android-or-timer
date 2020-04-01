package com.michaelcorral.ortimer.screens.mainscreen

import com.michaelcorral.ortimer.base.BasePresenter
import com.michaelcorral.ortimer.base.BaseView
import com.michaelcorral.ortimer.data.local.TimeEntry

interface MainScreenContract {

    interface View: BaseView {

        fun initializeViews()
        fun displayTimeEntries(timeEntries: List<TimeEntry>)
        fun displayTimeEntryInList(timeEntry: TimeEntry)
        fun updateTimeEntryInList(timeEntry: TimeEntry, index: Int)
        fun clearTimeEntries()
        fun removeTimeEntry(index: Int)
        fun startSession()
        fun stopSession()
        fun unbindService()
        fun togglePlayButton()
        fun toggleStopButton()
        fun toggleAddButton(isEnabled: Boolean)
        fun toggleEmptyState(isEnabled: Boolean)
    }

    interface Presenter : BasePresenter {

        fun onPlayButtonClicked()
        fun removeTimeEntry(timeEntryToBeRemoved: EditedTimeEntry)
    }
}