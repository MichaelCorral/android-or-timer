package com.michaelcorral.ortimer.data.local

import com.michaelcorral.ortimer.data.TimeEntryDataSource
import io.reactivex.Single

interface TimeEntryLocalDataSource : TimeEntryDataSource {

    fun saveTimeEntry(description: String): Single<Long>
}