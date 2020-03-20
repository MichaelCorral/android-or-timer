package com.michaelcorral.ortimer.screens.settings.about

import com.michaelcorral.ortimer.BuildConfig.VERSION_NAME

class AboutPresenter(
    private var view: AboutContract.View?
) : AboutContract.Presenter {

    override fun setup() {
        view?.initializeViews()
        view?.displayOrTimerVersion(VERSION_NAME)
    }

    override fun detachView() {
        view = null
    }
}