package com.michaelcorral.ortimer.base

interface BaseView {

    fun showLoading()
    fun hideLoading()

    fun showMessage(message: String)
}