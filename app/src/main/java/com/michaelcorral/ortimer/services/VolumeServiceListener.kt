package com.michaelcorral.ortimer.services

import com.michaelcorral.ortimer.data.local.TimeEntry

interface VolumeServiceListener {

    fun displayTimeEntryInList(timeEntry: TimeEntry)
    fun toggleStopButton()
    fun toggleAddButton(isEnabled: Boolean)
}