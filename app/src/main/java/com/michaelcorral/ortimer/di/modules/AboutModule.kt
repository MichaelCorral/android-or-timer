package com.michaelcorral.ortimer.di.modules

import com.michaelcorral.ortimer.screens.settings.about.AboutActivity
import com.michaelcorral.ortimer.screens.settings.about.AboutContract
import com.michaelcorral.ortimer.screens.settings.about.AboutPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val aboutModule = module {

    scope(named<AboutActivity>()) {
        scoped<AboutContract.Presenter> { (view: AboutContract.View) ->
            AboutPresenter(view)
        }
    }
}