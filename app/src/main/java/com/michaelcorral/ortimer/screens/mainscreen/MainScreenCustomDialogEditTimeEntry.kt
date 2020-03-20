package com.michaelcorral.ortimer.screens.mainscreen

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.michaelcorral.ortimer.R
import kotlinx.android.synthetic.main.mainscreen_layout_custom_dialog.*

class MainScreenCustomDialogEditTimeEntry(
    context: Context,
    styles: Int,
    private val timeEntryToBeEdited: EditedTimeEntry,
    private val onSaveClicked: (EditedTimeEntry) -> Unit
) : Dialog(context, styles) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainscreen_layout_custom_dialog)

        mainScreenLayoutCustomDialogTextViewTime.text = timeEntryToBeEdited.timeEntry.time
        mainScreenLayoutCustomDialogEditTextDescription.setText(timeEntryToBeEdited.timeEntry.description)

        mainScreenLayoutCustomDialogButtonSave.setOnClickListener {
            timeEntryToBeEdited.timeEntry.description = mainScreenLayoutCustomDialogEditTextDescription.text.toString()
            onSaveClicked(timeEntryToBeEdited)
        }
    }
}