package com.michaelcorral.ortimer.screens.mainscreen.dialogs.addtimeentry

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.data.local.TimeEntry
import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager
import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager.Key.TimePreferenceKey
import com.michaelcorral.ortimer.utils.extensions.currentTime
import com.michaelcorral.ortimer.utils.extensions.today
import kotlinx.android.synthetic.main.mainscreen_layout_custom_dialog.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class MainScreenDialogAddTimeEntry(
    context: Context,
    styles: Int,
    private val displayTimeEntryInList: (TimeEntry) -> Unit
) : Dialog(context, styles), KoinComponent, MainScreenDialogAddTimeEntryContract.View {

    private val presenter: MainScreenDialogAddTimeEntryContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainscreen_layout_custom_dialog)

        presenter.setup()
    }

    override fun initializeViews(currentTime: String) {
        initializeSaveButton(currentTime)
        initializeCurrentTimeTextView(currentTime)
    }

    private fun initializeSaveButton(currentTime: String) {
        mainScreenLayoutCustomDialogButtonSave.setOnClickListener {
            val description = mainScreenLayoutCustomDialogEditTextDescription.text.toString()

            val timeEntry = TimeEntry(
                id = UUID.randomUUID().toString(),
                description = description,
                time = currentTime,
                dateCreated = Date().today()
            )

            presenter.saveTimeEntry(timeEntry)
        }
    }

    private fun initializeCurrentTimeTextView(currentTime: String) {
        mainScreenLayoutCustomDialogTextViewTime.text = currentTime
    }

    override fun displayTimeEntry(timeEntry: TimeEntry) {
        displayTimeEntryInList(timeEntry)
        dismiss()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}