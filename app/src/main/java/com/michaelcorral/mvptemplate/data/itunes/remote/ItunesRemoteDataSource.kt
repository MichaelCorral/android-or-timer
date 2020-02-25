package com.michaelcorral.mvptemplate.data.itunes.remote

import com.michaelcorral.mvptemplate.data.itunes.ItunesDataSource
import com.michaelcorral.mvptemplate.api.models.ItunesContentResponse
import io.reactivex.Single

interface ItunesRemoteDataSource : ItunesDataSource {

    fun fetchItunesContent(): Single<ItunesContentResponse>
}