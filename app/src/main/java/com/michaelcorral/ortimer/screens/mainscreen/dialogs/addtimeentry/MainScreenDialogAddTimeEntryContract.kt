package com.michaelcorral.ortimer.screens.mainscreen.dialogs.addtimeentry

import com.michaelcorral.ortimer.data.local.TimeEntry

interface MainScreenDialogAddTimeEntryContract {

    interface View {

        fun initializeViews(currentTime: String)
        fun displayTimeEntry(timeEntry: TimeEntry)
        fun showMessage(message: String)
    }

    interface Presenter {

        fun setup()
        fun saveTimeEntry(timeEntry: TimeEntry)
    }
}