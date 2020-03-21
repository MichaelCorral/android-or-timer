package com.michaelcorral.ortimer.screens.settings.dialogs

import com.michaelcorral.ortimer.data.TimeEntryRepository

class SettingsCustomDialogTimePreferencePickerPresenter(
    private var view: SettingsCustomDialogTimePreferencePickerContract.View?,
    private val repository: TimeEntryRepository
) : SettingsCustomDialogTimePreferencePickerContract.Presenter {

    override fun setup() {
        view?.initializeViews()
        retrieveTimePreference()
    }

    private fun retrieveTimePreference() {
        val is24HourClock = repository.getTimePreference()

        if (is24HourClock) {
            view?.preSelect24HourClockRadioButton()
        } else {
            view?.preSelect12HourClockRadioButton()
        }
    }

    override fun saveTimePreference(timePreference: String) {
        val is24HourClock = timePreference.contains("24")
        repository.saveTimePreference(is24HourClock)
    }

    override fun detachView() {
        view = null
    }
}