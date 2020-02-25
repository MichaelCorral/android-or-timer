package com.michaelcorral.mvptemplate.data.itunes.local

import com.michaelcorral.mvptemplate.data.itunes.sharedpreferences.SharedPreferencesManager
import com.michaelcorral.mvptemplate.data.itunes.sharedpreferences.SharedPreferencesManager.Key.*
import com.michaelcorral.mvptemplate.api.models.ItunesContentResults
import com.michaelcorral.mvptemplate.utils.extensions.today
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ItunesLocalDataSourceImpl(private val itunesContentDao: ItunesContentDao) :
    ItunesLocalDataSource {

    override fun saveUserLastVisitDate(date: String) = SharedPreferencesManager.put(LastVisited, date)
    override fun fetchUserLastVisitDate(): String =
        SharedPreferencesManager.getString(LastVisited, defaultValue = Date().today())

    override fun saveLastScreenId(screenId: String) = SharedPreferencesManager.put(LastScreen, screenId)

    override fun fetchLastScreenId(): String = SharedPreferencesManager.getString(LastScreen)

    override fun deleteAllItunesConent(): Completable {
        return itunesContentDao
            .deleteAllItunesContent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveLastItunesContent(itunesItem: ItunesContentResults): Single<Long> {
        //TODO: make toContent()
        val itunesContent = ItunesContent(
            id = itunesItem.trackId ?: 0L,
            trackName = itunesItem.trackName ?: "",
            artworkUrl100 = itunesItem.artworkUrl100 ?: "",
            trackPrice = itunesItem.trackPrice.toString(),
            currency = itunesItem.currency ?: "",
            longDescription = itunesItem.longDescription ?: "",
            primaryGenreName = itunesItem.primaryGenreName ?: ""
        )

        return itunesContentDao
            .insertItunesContent(itunesContent)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchLastItunesContent(): Single<ItunesContent> {
        return itunesContentDao
            .queryItunesContent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}