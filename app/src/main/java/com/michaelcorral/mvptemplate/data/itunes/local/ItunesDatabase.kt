package com.michaelcorral.mvptemplate.data.itunes.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.michaelcorral.mvptemplate.data.itunes.local.ItunesContent
import com.michaelcorral.mvptemplate.data.itunes.local.ItunesContentDao

@Database(entities = [ItunesContent::class], version = 1)
abstract class ItunesDatabase : RoomDatabase() {
    abstract fun itunesContentDao(): ItunesContentDao
}