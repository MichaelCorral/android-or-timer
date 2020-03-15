package com.michaelcorral.ortimer.di.modules

import com.michaelcorral.ortimer.screens.mainscreen.MainScreenActivity
import com.michaelcorral.ortimer.screens.mainscreen.MainScreenContract
import com.michaelcorral.ortimer.screens.mainscreen.MainScreenPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainScreenModule = module {

    scope(named<MainScreenActivity>()) {
        scoped<MainScreenContract.Presenter> { (view: MainScreenContract.View) ->
            MainScreenPresenter(view, get())
        }
    }
}