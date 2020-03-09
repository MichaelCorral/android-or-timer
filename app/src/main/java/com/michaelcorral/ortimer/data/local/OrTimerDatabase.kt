package com.michaelcorral.ortimer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TimeEntry::class], version = 1)
abstract class OrTimerDatabase : RoomDatabase() {
    abstract fun timeEntryDao(): TimeEntryDao
}