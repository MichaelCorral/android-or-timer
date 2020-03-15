package com.michaelcorral.ortimer.services

import androidx.media.VolumeProviderCompat
import com.michaelcorral.ortimer.base.BasePresenter
import com.michaelcorral.ortimer.data.local.TimeEntry

interface VolumeServiceContract {

    interface View {

        fun setupMediaSessionCompat()
        fun setupVolumeProvider(): VolumeProviderCompat
        fun addTimeEntry(timeEntry: TimeEntry)
    }

    interface Presenter : BasePresenter {

        fun saveTimeEntry()
        fun saveSessionState(isInSession: Boolean)
    }
}