package com.michaelcorral.mvptemplate.screens.itunesdetails

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.michaelcorral.mvptemplate.R
import com.michaelcorral.mvptemplate.api.models.ItunesContentResults
import com.michaelcorral.mvptemplate.base.BasePresenter
import com.michaelcorral.mvptemplate.base.MvpActivity
import com.michaelcorral.mvptemplate.constants.ITUNES_ITEM_KEY
import kotlinx.android.synthetic.main.itunesdetails_activity.*
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

class ItunesDetailsActivity : MvpActivity(), ItunesDetailsContract.View {

    private val presenter: ItunesDetailsContract.Presenter by currentScope.inject { parametersOf(this) }

    private var itunesItem: ItunesContentResults? = null

    override fun getActivityLayout(): Int = R.layout.itunesdetails_activity

    override fun getActivityPresenter(): BasePresenter = presenter

    override fun onActivityReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onActivityReady(savedInstanceState, intent)

        presenter.setup()
    }

    override fun getItunesItemFromBundle(): ItunesContentResults? {
        itunesItem = intent.getParcelableExtra(ITUNES_ITEM_KEY)
        return itunesItem
    }

    override fun displayItunesItem(itunesItem: ItunesContentResults?) {
        itunesItem?.let { item ->
            Glide.with(this)
                .load(item.artworkUrl600)
                .into(itunesDetailsImageViewArtwork)

            itunesDetailsTextViewTrackName.text = item.trackName
            itunesDetailsTextViewGenre.text = item.primaryGenreName
            itunesDetailsTextViewDescription.text = item.longDescription
        }
    }
}