package com.michaelcorral.ortimer.di.modules

import com.michaelcorral.ortimer.services.VolumeService
import com.michaelcorral.ortimer.services.VolumeServiceContract
import com.michaelcorral.ortimer.services.VolumeServicePresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val volumeServiceModule = module {

    scope(named<VolumeService>()) {
        scoped<VolumeServiceContract.Presenter> { (view: VolumeServiceContract.View) ->
            VolumeServicePresenter(view, get())
        }
    }
}