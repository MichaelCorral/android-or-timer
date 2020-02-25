package com.michaelcorral.mvptemplate.api.services

import com.michaelcorral.mvptemplate.api.models.ItunesContentResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ItunesService {

    @GET("/search?term=star&amp;country=au&amp;media=movie&amp;all")
    fun getItunesContent(): Single<ItunesContentResponse>
}