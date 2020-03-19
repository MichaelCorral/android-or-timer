package com.michaelcorral.ortimer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
}