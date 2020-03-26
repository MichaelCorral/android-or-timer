package com.michaelcorral.ortimer.screens.mainscreen

import com.michaelcorral.ortimer.constants.SOMETHING_WENT_WRONG
import com.michaelcorral.ortimer.constants.TIME_ENTRY_ADDED
import com.michaelcorral.ortimer.constants.TIME_ENTRY_REMOVED
import com.michaelcorral.ortimer.constants.TIME_ENTRY_UPDATED
import com.michaelcorral.ortimer.data.TimeEntryRepository
import com.michaelcorral.ortimer.data.local.TimeEntry
import io.reactivex.disposables.CompositeDisposable

class MainScreenPresenter(
    private var view: MainScreenContract.View?,
    private val repository: TimeEntryRepository
) : MainScreenContract.Presenter {

    private var toggleSession = false

    private val compositeDisposable = CompositeDisposable()

    override fun setup() {
        // TODO: Refactor to factory or some kind of polymorphism
        toggleSession = repository.retrieveSessionState()
        if (toggleSession) {
            startSession()
        }

        view?.initializeViews()
        retrieveTimeEntries()
    }

    private fun retrieveTimeEntries() {
        compositeDisposable.add(
            repository
                .retrieveTimeEntries()
                .subscribe(this::onRetrieveTimeEntriesSuccess) { onRetrieveTimeEntriesFailed() }
        )
    }

    private fun onRetrieveTimeEntriesSuccess(timeEntries: List<TimeEntry>) {
        view?.displayTimeEntries(timeEntries)
    }

    private fun onRetrieveTimeEntriesFailed() {
        view?.showMessage(SOMETHING_WENT_WRONG)
    }

    override fun onPlayButtonClicked() {
        toggleSession = !toggleSession
        if (toggleSession) {
            removeAllTimeEntries()
            startSession()
        } else {
            stopSession()
        }
    }

    private fun removeAllTimeEntries() {
        compositeDisposable.add(
            repository
                .removeAllTimeEntries()
                .subscribe({ onRemoveAllTimeEntriesSuccessful() }, { onRemoveAllTimeEntriesFailed() })
        )
    }

    private fun onRemoveAllTimeEntriesSuccessful() {
        view?.clearTimeEntries()
    }

    private fun onRemoveAllTimeEntriesFailed() {
        view?.showMessage(SOMETHING_WENT_WRONG)
    }

    private fun startSession() {
        repository.saveSessionState(true)
        view?.startSession()
        view?.togglePlayButton()
        view?.toggleAddButton(true)
    }

    private fun stopSession() {
        repository.saveSessionState(false)
        view?.stopSession()
        view?.toggleStopButton()
        view?.toggleAddButton(false)
    }

    override fun removeTimeEntry(timeEntryToBeRemoved: EditedTimeEntry) {
        compositeDisposable.add(
            repository
                .removeTimeEntry(timeEntryToBeRemoved.timeEntry)
                .subscribe({ onRemoveTimeEntrySuccess(timeEntryToBeRemoved.index) }, { onRemoveTimeEntryFailed() })
        )
    }

    private fun onRemoveTimeEntrySuccess(index: Int) {
        view?.removeTimeEntry(index)
        view?.showMessage(TIME_ENTRY_REMOVED)
    }

    private fun onRemoveTimeEntryFailed() {
        view?.showMessage(SOMETHING_WENT_WRONG)
    }

    override fun detachView() {
        view?.unbindService()
        view = null
        compositeDisposable.dispose()
    }
}