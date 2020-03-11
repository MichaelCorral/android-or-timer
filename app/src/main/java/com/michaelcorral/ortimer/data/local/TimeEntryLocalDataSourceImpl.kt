package com.michaelcorral.ortimer.data.local

import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager
import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager.Key.SessionStateKey
import com.michaelcorral.ortimer.utils.extensions.currentTime
import com.michaelcorral.ortimer.utils.extensions.today
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class TimeEntryLocalDataSourceImpl(private val timeEntryDao: TimeEntryDao) :
    TimeEntryLocalDataSource {

    override fun saveTimeEntry(description: String): Single<Long> {
        val timeEntry = TimeEntry(
            id = UUID.randomUUID().toString(),
            description = description,
            timeEntry = Date().currentTime(),
            dateCreated = Date().today()
        )

        return timeEntryDao
            .insertTimeEntry(timeEntry)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveSessionState(isInSession: Boolean) {
        SharedPreferencesManager.put(SessionStateKey, isInSession)
    }
}