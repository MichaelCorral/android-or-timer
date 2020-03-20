package com.michaelcorral.ortimer.screens.mainscreen

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.data.local.TimeEntry
import com.michaelcorral.ortimer.utils.extensions.currentTime
import com.michaelcorral.ortimer.utils.extensions.today
import kotlinx.android.synthetic.main.mainscreen_layout_custom_dialog.*
import java.util.*

class MainScreenCustomDialogAddTimeEntry(
    context: Context,
    styles: Int,
    private val onSaveClicked: (TimeEntry) -> Unit
) : Dialog(context, styles) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainscreen_layout_custom_dialog)

        val currentTime = Date().currentTime()

        mainScreenLayoutCustomDialogTextViewTime.text = currentTime

        mainScreenLayoutCustomDialogButtonSave.setOnClickListener {
            val description = mainScreenLayoutCustomDialogEditTextDescription.text.toString()

            val timeEntry = TimeEntry(
                id = UUID.randomUUID().toString(),
                description = description,
                time = currentTime,
                dateCreated = Date().today()
            )

            onSaveClicked(timeEntry)
        }
    }
}