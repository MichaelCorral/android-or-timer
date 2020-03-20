package com.michaelcorral.ortimer.screens.settings

class SettingsPresenter(
    private var view: SettingsContract.View?
): SettingsContract.Presenter {

    override fun setup() {
        view?.initializeViews()
    }

    override fun detachView() {
        view = null
    }
}