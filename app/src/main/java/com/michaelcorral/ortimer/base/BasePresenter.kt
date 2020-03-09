package com.michaelcorral.ortimer.base

interface BasePresenter {

    fun setup(toggleSession: Boolean)

    // We must detach the view when destroyed to prevent memory leaks
    fun detachView()
}