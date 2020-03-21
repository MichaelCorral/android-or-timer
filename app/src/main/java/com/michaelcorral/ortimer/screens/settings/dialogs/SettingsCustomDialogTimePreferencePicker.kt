package com.michaelcorral.ortimer.screens.settings.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.RadioButton
import com.michaelcorral.ortimer.R
import kotlinx.android.synthetic.main.settings_layout_custom_dialog.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class SettingsCustomDialogTimePreferencePicker(
    context: Context,
    styles: Int
) : Dialog(context, styles), KoinComponent, SettingsCustomDialogTimePreferencePickerContract.View {

    private val presenter: SettingsCustomDialogTimePreferencePickerContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_layout_custom_dialog)

        presenter.setup()
    }

    override fun initializeViews() {
        initializeSaveButton()
    }

    private fun initializeSaveButton() {
        settingsLayoutCustomDialogButtonSave.setOnClickListener {
            presenter.saveTimePreference(getSelectedTimePreference())
            dismiss()
        }
    }

    private fun getSelectedTimePreference(): String {
        val selectedRadioButtonId = settingsLayoutCustomDialogRadioGroup.checkedRadioButtonId
        val selectedRadioButton: RadioButton = findViewById(selectedRadioButtonId)
        return selectedRadioButton.tag.toString()
    }

    override fun preSelect24HourClockRadioButton() {
        settingsLayoutCustomDialogRadioButton24Hour.isChecked = true
    }

    override fun preSelect12HourClockRadioButton() {
        settingsLayoutCustomDialogRadioButton12Hour.isChecked = true
    }
}