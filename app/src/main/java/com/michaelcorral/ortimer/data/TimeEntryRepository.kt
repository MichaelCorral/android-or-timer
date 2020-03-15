package com.michaelcorral.ortimer.data

import com.michaelcorral.ortimer.data.local.TimeEntry
import com.michaelcorral.ortimer.data.local.TimeEntryLocalDataSource
import io.reactivex.Single
import timber.log.Timber

class TimeEntryRepository(
    private val localDataSource: TimeEntryLocalDataSource
) : TimeEntryLocalDataSource {

    override fun saveTimeEntry(timeEntry: TimeEntry): Single<Long> {
        return localDataSource.saveTimeEntry(timeEntry)
    }

    override fun retrieveTimeEntries(): Single<List<TimeEntry>> {
        return localDataSource.retrieveTimeEntries()
    }

    override fun saveSessionState(isInSession: Boolean) {
        localDataSource.saveSessionState(isInSession)
    }

    fun test() {
        Timber.d("REPOSITORY TEST")
    }
}