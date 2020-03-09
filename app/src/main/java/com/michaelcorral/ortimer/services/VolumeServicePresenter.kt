package com.michaelcorral.ortimer.services

class VolumeServicePresenter(private var view: VolumeServiceContract.View?) :
    VolumeServiceContract.Presenter {

    override fun setup(toggleSession: Boolean) {

    }

    override fun detachView() {
        view = null
    }
}