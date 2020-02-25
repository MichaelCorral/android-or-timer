package com.michaelcorral.mvptemplate.di.modules

import com.michaelcorral.mvptemplate.screens.mainscreen.MainScreenActivity
import com.michaelcorral.mvptemplate.screens.mainscreen.MainScreenContract
import com.michaelcorral.mvptemplate.screens.mainscreen.MainScreenPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainScreenModule = module {

    scope(named<MainScreenActivity>()) {
        scoped<MainScreenContract.Presenter> { (view: MainScreenContract.View) ->
            MainScreenPresenter(view, get())
        }
    }
}