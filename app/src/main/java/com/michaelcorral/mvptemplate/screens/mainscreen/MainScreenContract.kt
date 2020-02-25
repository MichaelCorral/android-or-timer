package com.michaelcorral.mvptemplate.screens.mainscreen

import com.michaelcorral.mvptemplate.api.models.ItunesContentResults
import com.michaelcorral.mvptemplate.base.BasePresenter
import com.michaelcorral.mvptemplate.base.BaseView

interface MainScreenContract {

    interface View : BaseView {
        fun redirectToLastScreen(screenId: String)
        fun displayUserLastVisited(date: String)
        fun displayItunesContent(itunesResults: List<ItunesContentResults>)
        fun redirectToItunesDetailsScreen(itunesItem: ItunesContentResults)
    }

    interface Presenter : BasePresenter {
        fun onItunesContentClick(itunesItem: ItunesContentResults)
    }
}