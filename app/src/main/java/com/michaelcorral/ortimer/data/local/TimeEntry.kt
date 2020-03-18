package com.michaelcorral.ortimer.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Time_Entry")
data class TimeEntry(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "date_created")
    val dateCreated: String
)