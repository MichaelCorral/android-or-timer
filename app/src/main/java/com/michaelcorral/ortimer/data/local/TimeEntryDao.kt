package com.michaelcorral.ortimer.data.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TimeEntryDao {

    @Insert
    fun insertTimeEntry(timeEntry: TimeEntry): Single<Long>

    @Query("SELECT * FROM Time_Entry")
    fun queryTimeEntries(): Single<List<TimeEntry>>

    @Update
    fun updateTimeEntry(timeEntry: TimeEntry): Completable

    @Query("DELETE FROM Time_Entry")
    fun deleteAllTimeEntries(): Completable

    @Delete
    fun deleteTimeEntry(timeEntry: TimeEntry): Completable
}