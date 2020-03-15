package com.michaelcorral.ortimer.services

import com.michaelcorral.ortimer.data.local.TimeEntry

interface VolumeServiceListener {

    fun addTimeEntry(timeEntry: TimeEntry)
}