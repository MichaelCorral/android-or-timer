package com.michaelcorral.mvptemplate.base

interface BasePresenter {

    fun setup()

    // We must detach the view when destroyed to prevent memory leaks
    fun detachView()
}