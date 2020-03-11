package com.michaelcorral.ortimer.services

import com.michaelcorral.ortimer.data.TimeEntryRepository

class VolumeServicePresenter(
    private var view: VolumeServiceContract.View?,
    private val repository: TimeEntryRepository) : VolumeServiceContract.Presenter {

    override fun setup() {
        saveSessionState(true)
        view?.setupMediaSessionCompat()
    }

    override fun saveTimeEntry() {
        repository.saveTimeEntry()
    }

    override fun saveSessionState(isInSession: Boolean) {
        repository.saveSessionState(isInSession)
    }

    override fun detachView() {
        view = null
    }
}