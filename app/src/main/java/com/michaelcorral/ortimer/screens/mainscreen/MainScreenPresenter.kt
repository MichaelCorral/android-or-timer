package com.michaelcorral.ortimer.screens.mainscreen

class MainScreenPresenter(private var view: MainScreenContract.View?) :
    MainScreenContract.Presenter {

    private var toggleSession = false

    override fun setup() {
        view?.initializeViews()
    }

    override fun onPlayButtonClicked() {
        toggleSession = !toggleSession
        if (toggleSession) {
            startSession()
        } else {
            stopSession()
        }
    }

    private fun startSession() {
        view?.startVolumeService()
        view?.togglePlayButton()
    }

    private fun stopSession() {
        view?.stopVolumeService()
        view?.toggleStopButton()
    }

    override fun detachView() {
        view = null
    }
}