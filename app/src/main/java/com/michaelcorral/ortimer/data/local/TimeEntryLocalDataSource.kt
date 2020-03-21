package com.michaelcorral.ortimer.data.local

import com.michaelcorral.ortimer.data.TimeEntryDataSource
import io.reactivex.Completable
import io.reactivex.Single

interface TimeEntryLocalDataSource : TimeEntryDataSource {

    fun saveTimeEntry(timeEntry: TimeEntry): Single<Long>
    fun retrieveTimeEntries(): Single<List<TimeEntry>>

    fun saveSessionState(isInSession: Boolean)
    fun retrieveSessionState(): Boolean

    fun editTimeEntry(timeEntry: TimeEntry): Completable

    fun removeAllTimeEntries(): Completable
    fun removeTimeEntry(timeEntry: TimeEntry): Completable

    fun saveTimePreference(is24HourClock: Boolean)
    fun getTimePreference(): Boolean
}