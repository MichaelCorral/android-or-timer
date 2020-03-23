package com.michaelcorral.ortimer.screens.mainscreen.dialogs.edittimeentry

import com.michaelcorral.ortimer.data.local.TimeEntry
import com.michaelcorral.ortimer.screens.mainscreen.EditedTimeEntry

interface MainScreenDialogEditTimeEntryContract {

    interface View {

        fun initializeViews()
        fun updateTimeEntry(timeEntry: TimeEntry, index: Int)
        fun showMessage(message: String)
    }

    interface Presenter {

        fun setup()
        fun editTimeEntry(editedTimeEntry: EditedTimeEntry)
    }
}