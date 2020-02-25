package com.michaelcorral.mvptemplate.screens.itunesdetails

import com.michaelcorral.mvptemplate.api.models.ItunesContentResults
import com.michaelcorral.mvptemplate.base.BasePresenter

interface ItunesDetailsContract {

    interface View {
        fun getItunesItemFromBundle(): ItunesContentResults?
        fun displayItunesItem(itunesItem: ItunesContentResults?)
    }

    interface Presenter: BasePresenter {

    }
}