package com.michaelcorral.ortimer.data

import com.michaelcorral.ortimer.data.local.TimeEntryLocalDataSource
import io.reactivex.Single

class TimeEntryRepository(
    private val localDataSource: TimeEntryLocalDataSource
) : TimeEntryLocalDataSource {

    override fun saveTimeEntry(description: String): Single<Long> {
        return localDataSource.saveTimeEntry(description)
    }

}