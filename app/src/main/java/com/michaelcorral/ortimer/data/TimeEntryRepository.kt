package com.michaelcorral.ortimer.data

import com.michaelcorral.ortimer.data.local.TimeEntryLocalDataSource

class TimeEntryRepository(
    private val localDataSource: TimeEntryLocalDataSource
) : TimeEntryLocalDataSource {

    override fun saveTimeEntry() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}