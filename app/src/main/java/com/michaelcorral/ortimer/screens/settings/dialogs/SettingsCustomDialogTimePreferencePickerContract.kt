package com.michaelcorral.ortimer.screens.settings.dialogs

interface SettingsCustomDialogTimePreferencePickerContract {

    interface View {

        fun initializeViews()
        fun preSelect24HourClockRadioButton()
        fun preSelect12HourClockRadioButton()
    }

    interface Presenter {

        fun setup()

        fun saveTimePreference(timePreference: String)
    }
}