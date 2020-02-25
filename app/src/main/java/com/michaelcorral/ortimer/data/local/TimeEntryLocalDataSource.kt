package com.michaelcorral.ortimer.data.local

import com.michaelcorral.ortimer.data.TimeEntryDataSource

interface TimeEntryLocalDataSource : TimeEntryDataSource {

    fun saveTimeEntry()
}