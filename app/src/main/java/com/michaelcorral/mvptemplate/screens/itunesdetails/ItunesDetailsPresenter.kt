package com.michaelcorral.mvptemplate.screens.itunesdetails


import com.michaelcorral.mvptemplate.api.models.ItunesContentResults
import com.michaelcorral.mvptemplate.data.itunes.ItunesRepository
import com.michaelcorral.mvptemplate.screens.itunesdetails.ItunesDetailsContract
import io.reactivex.disposables.CompositeDisposable

class ItunesDetailsPresenter(
    private var view: ItunesDetailsContract.View?,
    private val repository: ItunesRepository
) : ItunesDetailsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    private var itunesItem: ItunesContentResults? = null

    override fun setup() {
        itunesItem = view?.getItunesItemFromBundle()

        if (itunesItem == null) {
            fetchLastItunesContent()
        } else {
            view?.displayItunesItem(itunesItem)
        }
    }

    private fun removeLastItunesContent() {
        compositeDisposable.add(
            repository
                .deleteAllItunesConent()
                .subscribe { saveLastItunesContent() }
        )
    }

    private fun saveLastItunesContent() {
        itunesItem?.let { itunesItem ->
            compositeDisposable.add(
                repository
                    .saveLastItunesContent(itunesItem)
                    .subscribe()
            )
        }
    }

    private fun fetchLastItunesContent() {
        compositeDisposable.add(
            repository
                .fetchLastItunesContent()
                .subscribe { itunesContent ->
                    itunesItem = itunesContent.toItunesContentResults(itunesContent)
                    view?.displayItunesItem(itunesItem)
                }
        )
    }

    override fun detachView() {
        view = null
        compositeDisposable.dispose()
    }
}