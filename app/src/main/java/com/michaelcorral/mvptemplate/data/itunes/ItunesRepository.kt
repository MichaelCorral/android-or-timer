package com.michaelcorral.mvptemplate.data.itunes

import com.michaelcorral.mvptemplate.api.models.ItunesContentResponse
import com.michaelcorral.mvptemplate.api.models.ItunesContentResults
import com.michaelcorral.mvptemplate.data.itunes.local.ItunesContent
import com.michaelcorral.mvptemplate.data.itunes.local.ItunesLocalDataSource
import com.michaelcorral.mvptemplate.data.itunes.remote.ItunesRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ItunesRepository(
    private val localDataSource: ItunesLocalDataSource,
    private val remoteDataSource: ItunesRemoteDataSource
) : ItunesLocalDataSource, ItunesRemoteDataSource {

    override fun fetchItunesContent(): Single<ItunesContentResponse> =
        remoteDataSource.fetchItunesContent()

    override fun saveUserLastVisitDate(date: String) = localDataSource.saveUserLastVisitDate(date)
    override fun fetchUserLastVisitDate(): String = localDataSource.fetchUserLastVisitDate()

    override fun saveLastScreenId(screenId: String) = localDataSource.saveLastScreenId(screenId)
    override fun fetchLastScreenId(): String = localDataSource.fetchLastScreenId()

    override fun deleteAllItunesConent(): Completable =
        localDataSource.deleteAllItunesConent()

    override fun saveLastItunesContent(itunesItem: ItunesContentResults): Single<Long> =
        localDataSource.saveLastItunesContent(itunesItem)

    override fun fetchLastItunesContent(): Single<ItunesContent> =
        localDataSource.fetchLastItunesContent()
}