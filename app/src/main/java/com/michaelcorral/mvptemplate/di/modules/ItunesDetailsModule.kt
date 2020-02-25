package com.michaelcorral.mvptemplate.di.modules

import com.michaelcorral.mvptemplate.screens.itunesdetails.ItunesDetailsActivity
import com.michaelcorral.mvptemplate.screens.itunesdetails.ItunesDetailsContract
import com.michaelcorral.mvptemplate.screens.itunesdetails.ItunesDetailsPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val itunesDetailsModule = module {

    scope(named<ItunesDetailsActivity>()) {
        scoped<ItunesDetailsContract.Presenter> { (view: ItunesDetailsContract.View) ->
            ItunesDetailsPresenter(view, get())
        }
    }
}