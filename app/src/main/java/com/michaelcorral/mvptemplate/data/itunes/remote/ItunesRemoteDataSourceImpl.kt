package com.michaelcorral.mvptemplate.data.itunes.remote

import com.michaelcorral.mvptemplate.api.models.ItunesContentResponse
import com.michaelcorral.mvptemplate.api.services.ItunesService
import com.michaelcorral.mvptemplate.data.itunes.remote.ItunesRemoteDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class ItunesRemoteDataSourceImpl(private val itunesService: ItunesService) :
    ItunesRemoteDataSource {

    override fun fetchItunesContent(): Single<ItunesContentResponse> {
        return itunesService
            .getItunesContent()
            .observeOn(AndroidSchedulers.mainThread())
    }
}