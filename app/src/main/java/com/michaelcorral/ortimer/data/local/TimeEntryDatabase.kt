package com.michaelcorral.ortimer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TimeEntry::class], version = 1)
abstract class TimeEntryDatabase : RoomDatabase() {
    abstract fun timeEntryDao(): TimeEntryDao
}