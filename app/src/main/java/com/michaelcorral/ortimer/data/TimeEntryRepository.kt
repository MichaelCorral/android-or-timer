package com.michaelcorral.ortimer.data

import com.michaelcorral.ortimer.data.local.TimeEntry
import com.michaelcorral.ortimer.data.local.TimeEntryLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

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

    override fun retrieveSessionState(): Boolean = localDataSource.retrieveSessionState()

    override fun editTimeEntry(timeEntry: TimeEntry): Completable {
        return localDataSource.editTimeEntry(timeEntry)
    }
}