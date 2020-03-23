package com.michaelcorral.ortimer.screens.mainscreen.dialogs.addtimeentry

import com.michaelcorral.ortimer.constants.SOMETHING_WENT_WRONG
import com.michaelcorral.ortimer.constants.TIME_ENTRY_ADDED
import com.michaelcorral.ortimer.data.TimeEntryRepository
import com.michaelcorral.ortimer.data.local.TimeEntry
import com.michaelcorral.ortimer.utils.extensions.currentTime
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class MainScreenDialogAddTimeEntryPresenter(
    private val view: MainScreenDialogAddTimeEntryContract.View,
    private val repository: TimeEntryRepository
) : MainScreenDialogAddTimeEntryContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun setup() {
        view.initializeViews(getCurrentTime())
    }

    private fun getCurrentTime(): String {
        val timePreference = repository.getTimePreference()
        return Date().currentTime(timePreference)
    }

    override fun saveTimeEntry(timeEntry: TimeEntry) {
        compositeDisposable.add(
            repository
                .saveTimeEntry(timeEntry)
                .subscribe({ onSaveTimeEntrySuccess(timeEntry) }, { onSaveTimeEntryFailed() })
        )
    }

    private fun onSaveTimeEntrySuccess(timeEntry: TimeEntry) {
        view.displayTimeEntry(timeEntry)
        view.showMessage(TIME_ENTRY_ADDED)
    }

    private fun onSaveTimeEntryFailed() {
        view.showMessage(SOMETHING_WENT_WRONG)
    }
}