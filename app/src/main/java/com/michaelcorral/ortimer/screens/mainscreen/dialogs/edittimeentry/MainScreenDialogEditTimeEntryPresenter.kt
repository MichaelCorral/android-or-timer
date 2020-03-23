package com.michaelcorral.ortimer.screens.mainscreen.dialogs.edittimeentry

import com.michaelcorral.ortimer.constants.SOMETHING_WENT_WRONG
import com.michaelcorral.ortimer.constants.TIME_ENTRY_UPDATED
import com.michaelcorral.ortimer.data.TimeEntryRepository
import com.michaelcorral.ortimer.screens.mainscreen.EditedTimeEntry
import io.reactivex.disposables.CompositeDisposable

class MainScreenDialogEditTimeEntryPresenter(
    private val view: MainScreenDialogEditTimeEntryContract.View,
    private val repository: TimeEntryRepository
) : MainScreenDialogEditTimeEntryContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun setup() {
        view.initializeViews()
    }

    override fun editTimeEntry(editedTimeEntry: EditedTimeEntry) {
        compositeDisposable.add(
            repository
                .editTimeEntry(editedTimeEntry.timeEntry)
                .subscribe({ onEditTimeEntrySuccess(editedTimeEntry) }, { onEditTimeEntryFailed() })
        )
    }

    private fun onEditTimeEntrySuccess(editedTimeEntry: EditedTimeEntry) {
        view.updateTimeEntry(
            editedTimeEntry.timeEntry,
            editedTimeEntry.index
        )

        view.showMessage(TIME_ENTRY_UPDATED)
    }

    private fun onEditTimeEntryFailed() {
        view.showMessage(SOMETHING_WENT_WRONG)
    }
}