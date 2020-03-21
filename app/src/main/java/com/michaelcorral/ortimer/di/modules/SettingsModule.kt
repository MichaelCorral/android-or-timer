package com.michaelcorral.ortimer.di.modules

import com.michaelcorral.ortimer.screens.settings.SettingsActivity
import com.michaelcorral.ortimer.screens.settings.SettingsContract
import com.michaelcorral.ortimer.screens.settings.SettingsPresenter
import com.michaelcorral.ortimer.screens.settings.dialogs.SettingsCustomDialogTimePreferencePickerContract
import com.michaelcorral.ortimer.screens.settings.dialogs.SettingsCustomDialogTimePreferencePickerPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val settingsModule = module {

    scope(named<SettingsActivity>()) {
        scoped<SettingsContract.Presenter> { (view: SettingsContract.View) ->
            SettingsPresenter(view)
        }
    }

    factory<SettingsCustomDialogTimePreferencePickerContract.Presenter> { (view: SettingsCustomDialogTimePreferencePickerContract.View) ->
        SettingsCustomDialogTimePreferencePickerPresenter(view, get())
    }
}