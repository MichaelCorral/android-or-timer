package com.michaelcorral.ortimer.screens.mainscreen

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
//        this.toggleSession = toggleSession
        view?.initializeViews()
        retrieveTimeEntries()
    }

    private fun retrieveTimeEntries() {
        compositeDisposable.add(
            repository.retrieveTimeEntries()
                .subscribe(this::onRetrieveTimeEntriesSuccess, this::onRetrieveTimeEntriesFailed)
        )
    }

    private fun onRetrieveTimeEntriesSuccess(timeEntries: List<TimeEntry>) {
        view?.displayTimeEntries(timeEntries)
    }

    private fun onRetrieveTimeEntriesFailed(throwable: Throwable) {

    }

    override fun onPlayButtonClicked() {
        toggleSession = !toggleSession
        if (toggleSession) {
            startSession()
        } else {
            stopSession()
        }
    }

    private fun startSession() {
        view?.startVolumeService()
        view?.togglePlayButton()
    }

    private fun stopSession() {
        view?.stopVolumeService()
        view?.toggleStopButton()
    }

    override fun detachView() {
        view = null
        compositeDisposable.dispose()
    }
}