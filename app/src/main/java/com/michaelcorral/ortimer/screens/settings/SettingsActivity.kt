package com.michaelcorral.ortimer.screens.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.base.BasePresenter
import com.michaelcorral.ortimer.base.OrTimerActivity
import com.michaelcorral.ortimer.screens.settings.about.AboutActivity
import kotlinx.android.synthetic.main.settings_activity.*
import kotlinx.android.synthetic.main.toolbar_layout_simple.*
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

class SettingsActivity : OrTimerActivity(), SettingsContract.View {

    private val presenter: SettingsContract.Presenter by currentScope.inject { parametersOf(this) }

    override fun getActivityLayout(): Int {
        return R.layout.settings_activity
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
        initializeAboutContainer()
    }

    private fun initializeToolbar() {
        setSupportActionBar(settingsToolbar as Toolbar)

        supportActionBar?.let { toolbar ->
            toolbar.title = getString(R.string.settings_title)
            toolbar.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun initializeAboutContainer() {
        settingsLinearLayoutAboutContainer.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}