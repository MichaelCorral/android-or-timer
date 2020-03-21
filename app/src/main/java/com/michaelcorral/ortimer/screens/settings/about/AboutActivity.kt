package com.michaelcorral.ortimer.screens.settings.about

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.base.BasePresenter
import com.michaelcorral.ortimer.base.OrTimerActivity
import kotlinx.android.synthetic.main.about_activity.*
import kotlinx.android.synthetic.main.toolbar_layout_simple.*
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

class AboutActivity : OrTimerActivity(), AboutContract.View {

    private val presenter: AboutContract.Presenter by currentScope.inject { parametersOf(this) }

    override fun getActivityLayout(): Int {
        return R.layout.about_activity
    }

    override fun getActivityPresenter(): BasePresenter {
        return presenter
    }

    override fun onActivityReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onActivityReady(savedInstanceState, intent)

        presenter.setup()
    }

    override fun initializeViews() {
        initializeToolbar()
    }

    private fun initializeToolbar() {
        setSupportActionBar(aboutToolbar as Toolbar)

        supportActionBar?.let { toolbar ->
            toolbar.title = getString(R.string.about_title)
            toolbar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun displayOrTimerVersion(version: String) {
        aboutTextViewVersion.text = version
    }
}
