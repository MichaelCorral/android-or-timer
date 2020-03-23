package com.michaelcorral.ortimer.screens.mainscreen.dialogs.edittimeentry

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.data.local.TimeEntry
import com.michaelcorral.ortimer.screens.mainscreen.EditedTimeEntry
import kotlinx.android.synthetic.main.mainscreen_layout_custom_dialog.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class MainScreenDialogEditTimeEntry(
    context: Context,
    styles: Int,
    private val timeEntryToBeEdited: EditedTimeEntry,
    private val updateTimeEntryInList: (TimeEntry, Int) -> Unit
) : Dialog(context, styles), KoinComponent, MainScreenDialogEditTimeEntryContract.View {

    private val presenter: MainScreenDialogEditTimeEntryContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainscreen_layout_custom_dialog)

        presenter.setup()
    }

    override fun initializeViews() {
        initializeTimeTextView()
        initializeDescription()
        initializeSaveButton()
    }

    private fun initializeTimeTextView() {
        mainScreenLayoutCustomDialogTextViewTime.text = timeEntryToBeEdited.timeEntry.time
    }

    private fun initializeDescription() {
        mainScreenLayoutCustomDialogEditTextDescription.setText(timeEntryToBeEdited.timeEntry.description)
    }

    private fun initializeSaveButton() {
        mainScreenLayoutCustomDialogButtonSave.setOnClickListener {
            timeEntryToBeEdited.timeEntry.description = mainScreenLayoutCustomDialogEditTextDescription.text.toString()
            presenter.editTimeEntry(timeEntryToBeEdited)
        }
    }

    override fun updateTimeEntry(timeEntry: TimeEntry, index: Int) {
        updateTimeEntryInList(timeEntry, index)
        dismiss()
    }

    override fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}