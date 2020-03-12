package com.michaelcorral.ortimer.services

import androidx.media.VolumeProviderCompat
import com.michaelcorral.ortimer.base.BasePresenter

interface VolumeServiceContract {

    interface View {

        fun setupMediaSessionCompat()
        fun setupVolumeProvider(): VolumeProviderCompat
        fun updateTimeEntries()
    }

    interface Presenter : BasePresenter {

        fun saveTimeEntry()
        fun saveSessionState(isInSession: Boolean)
    }
}