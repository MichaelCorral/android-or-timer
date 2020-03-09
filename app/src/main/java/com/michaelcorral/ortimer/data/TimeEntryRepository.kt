package com.michaelcorral.ortimer.data

import com.michaelcorral.ortimer.data.local.TimeEntryLocalDataSource
import timber.log.Timber

class TimeEntryRepository(
    private val localDataSource: TimeEntryLocalDataSource
) : TimeEntryLocalDataSource {

    override fun saveTimeEntry() {
        Timber.d("Repository: Save Time Entry")
    }

}