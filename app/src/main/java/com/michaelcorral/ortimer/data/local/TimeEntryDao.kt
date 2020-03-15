package com.michaelcorral.ortimer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface TimeEntryDao {

    @Insert
    fun insertTimeEntry(timeEntry: TimeEntry): Single<Long>

    @Query("SELECT * FROM Time_Entry")
    fun queryTimeEntries(): Single<List<TimeEntry>>
}