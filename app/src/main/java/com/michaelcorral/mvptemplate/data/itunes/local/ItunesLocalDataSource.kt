package com.michaelcorral.mvptemplate.data.itunes.local

import com.michaelcorral.mvptemplate.data.itunes.ItunesDataSource
import com.michaelcorral.mvptemplate.api.models.ItunesContentResults
import io.reactivex.Completable
import io.reactivex.Single

interface ItunesLocalDataSource : ItunesDataSource {

    fun saveUserLastVisitDate(date: String)
    fun fetchUserLastVisitDate(): String

    fun saveLastScreenId(screenId: String)
    fun fetchLastScreenId(): String

    fun deleteAllItunesConent(): Completable
    fun saveLastItunesContent(itunesItem: ItunesContentResults): Single<Long>
    fun fetchLastItunesContent(): Single<ItunesContent>
}