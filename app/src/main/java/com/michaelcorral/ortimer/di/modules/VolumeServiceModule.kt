package com.michaelcorral.ortimer.di.modules

import com.michaelcorral.ortimer.services.VolumeServiceContract
import com.michaelcorral.ortimer.services.VolumeServicePresenter
import org.koin.dsl.module

val volumeServiceModule = module {

    factory<VolumeServiceContract.Presenter> { (view: VolumeServiceContract.View) ->
        VolumeServicePresenter(view, get())
    }
}