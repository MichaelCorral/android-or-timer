package com.michaelcorral.ortimer.data.local

import androidx.room.Dao
import androidx.room.Insert
import io.reactivex.Single

@Dao
interface TimeEntryDao {

    @Insert
    fun insertTimeEntry(timeEntry: TimeEntry): Single<Long>
}