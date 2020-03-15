package com.michaelcorral.ortimer.data.local

import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager
import com.michaelcorral.ortimer.data.sharedpreferences.SharedPreferencesManager.Key.SessionStateKey
import com.michaelcorral.ortimer.utils.extensions.currentTime
import com.michaelcorral.ortimer.utils.extensions.today
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class TimeEntryLocalDataSourceImpl(private val timeEntryDao: TimeEntryDao) :
    TimeEntryLocalDataSource {

    override fun saveTimeEntry(timeEntry: TimeEntry): Single<Long> {
        return timeEntryDao
            .insertTimeEntry(timeEntry)
            .doOnError(Timber::e)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun retrieveTimeEntries(): Single<List<TimeEntry>> {
        return timeEntryDao
            .queryTimeEntries()
            .doOnError(Timber::e)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveSessionState(isInSession: Boolean) {
        SharedPreferencesManager.put(SessionStateKey, isInSession)
    }
}