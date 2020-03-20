package com.michaelcorral.ortimer.screens.settings.about

import com.michaelcorral.ortimer.base.BasePresenter

interface AboutContract {

    interface View {

        fun initializeViews()
        fun displayOrTimerVersion(version: String)
    }

    interface Presenter : BasePresenter {

    }
}