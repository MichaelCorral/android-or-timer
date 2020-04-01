package com.michaelcorral.ortimer.services

import com.michaelcorral.ortimer.constants.SOMETHING_WENT_WRONG
import com.michaelcorral.ortimer.constants.TIME_ENTRY_ADDED
import com.michaelcorral.ortimer.data.TimeEntryRepository
import com.michaelcorral.ortimer.data.local.TimeEntry
import com.michaelcorral.ortimer.utils.extensions.currentTime
import com.michaelcorral.ortimer.utils.extensions.today
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class VolumeServicePresenter(
    private var view: VolumeServiceContract.View?,
    private val repository: TimeEntryRepository
) : VolumeServiceContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun setup() {
        saveSessionState(true)
    }

    override fun saveTimeEntry() {
        val timePreference = repository.getTimePreference()

        val timeEntry = TimeEntry(
            id = UUID.randomUUID().toString(),
            description = "",
            time = Date().currentTime(timePreference),
            dateCreated = Date().today()
        )

        compositeDisposable.add(
            repository.saveTimeEntry(timeEntry)
                .subscribe({ onSaveTimeEntrySuccess(timeEntry) }, { onSaveTimeEntryFailed() } )
        )
    }

    private fun onSaveTimeEntrySuccess(timeEntry: TimeEntry) {
        view?.addTimeEntry(timeEntry)
        view?.showMessage(TIME_ENTRY_ADDED)
    }

    private fun onSaveTimeEntryFailed() {
        view?.showMessage(SOMETHING_WENT_WRONG)
    }

    override fun saveSessionState(isInSession: Boolean) {
        repository.saveSessionState(isInSession)
    }

    override fun detachView() {
        view = null
        compositeDisposable.dispose()
    }
}