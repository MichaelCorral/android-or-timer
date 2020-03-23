package com.michaelcorral.ortimer.screens.mainscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.base.BasePresenter
import com.michaelcorral.ortimer.base.OrTimerActivity
import com.michaelcorral.ortimer.data.local.TimeEntry
import com.michaelcorral.ortimer.screens.mainscreen.dialogs.addtimeentry.MainScreenDialogAddTimeEntry
import com.michaelcorral.ortimer.screens.mainscreen.dialogs.edittimeentry.MainScreenDialogEditTimeEntry
import com.michaelcorral.ortimer.screens.settings.SettingsActivity
import com.michaelcorral.ortimer.services.VolumeServiceListener
import kotlinx.android.synthetic.main.mainscreen_activity.*
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

class MainScreenActivity : OrTimerActivity(), MainScreenContract.View, VolumeServiceListener {

    private val presenter: MainScreenContract.Presenter by currentScope.inject { parametersOf(this) }

    private lateinit var timerSession: TimerSession

    private val adapter = MainScreenAdapter(
        onTimeEntryClicked = { timeEntryToBeEdited -> onTimeEntryClicked(timeEntryToBeEdited) },
        onTimeEntryRemoved = { timeEntryToBeRemoved -> onTimeEntryRemoved(timeEntryToBeRemoved) }
    )

    private lateinit var mainScreenDialogEditTimeEntry: MainScreenDialogEditTimeEntry
    private lateinit var mainScreenDialogAddTimeEntry: MainScreenDialogAddTimeEntry

    override fun getActivityLayout(): Int {
        return R.layout.mainscreen_activity
    }

    override fun getActivityPresenter(): BasePresenter {
        return presenter
    }

    override fun onActivityReady(savedInstanceState: Bundle?, intent: Intent) {
        super.onActivityReady(savedInstanceState, intent)

        presenter.setup()
    }

    override fun initializeViews() {
        initializeSettingsButton()
        initializePlayButton()
        initializeRecyclerView()
        initializeAddButton()
    }

    override fun displayTimeEntries(timeEntries: List<TimeEntry>) {
        mainScreenRecyclerView.visibility = View.VISIBLE
        mainScreenConstraintLayoutEmptyState.visibility = View.GONE
        adapter.addTimeEntries(timeEntries)
    }

    private fun initializeSettingsButton() {
        mainScreenImageViewSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun initializePlayButton() {
        mainScreenButtonPlay.setOnClickListener {
            presenter.onPlayButtonClicked()
        }
    }

    private fun initializeRecyclerView() {
        mainScreenRecyclerView.layoutManager = LinearLayoutManager(this)
        mainScreenRecyclerView.setHasFixedSize(true)
        mainScreenRecyclerView.adapter = adapter
    }

    private fun initializeAddButton() {
        mainScreenButtonAdd.setOnClickListener {
            mainScreenDialogAddTimeEntry =
                MainScreenDialogAddTimeEntry(
                    context = this,
                    styles = R.style.customDialogStyle,
                    displayTimeEntryInList = { timeEntry -> displayTimeEntryInList(timeEntry) }
                )
            mainScreenDialogAddTimeEntry.show()
        }
    }

    private fun onTimeEntryClicked(timeEntryToBeEdited: EditedTimeEntry) {
        mainScreenDialogEditTimeEntry =
            MainScreenDialogEditTimeEntry(
                context = this,
                styles = R.style.customDialogStyle,
                timeEntryToBeEdited = timeEntryToBeEdited,
                updateTimeEntryInList = { editedTimeEntry, index ->
                    updateTimeEntryInList(editedTimeEntry, index)
                }
            )

        mainScreenDialogEditTimeEntry.show()
    }

    private fun onTimeEntryRemoved(timeEntryToBeRemoved: EditedTimeEntry) {
        AlertDialog.Builder(this, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.mainscreen_delete))
            .setMessage(getString(R.string.mainscreen_are_u_sure_u_want_to_delete))
            .setNegativeButton(getString(R.string.mainscreen_no), null)
            .setPositiveButton(getString(R.string.mainscreen_yes)) { _, _ ->
                presenter.removeTimeEntry(timeEntryToBeRemoved)
            }
            .show()
    }

    override fun startSession() {
        timerSession = TimerSession(
            context = this,
            listener = this
        )

        timerSession.start()
    }

    override fun stopSession() {
        timerSession.stop()
    }

    override fun togglePlayButton() {
        mainScreenTextViewStartSession.text = getString(R.string.mainscreen_in_session)
        mainScreenButtonPlay.background = getDrawable(R.drawable.all_shape_stop_button)
    }

    override fun toggleStopButton() {
        mainScreenTextViewStartSession.text = getString(R.string.mainscreen_start_a_session)
        mainScreenButtonPlay.background = getDrawable(R.drawable.all_shape_play_button)
    }

    override fun toggleAddButton(isEnabled: Boolean) {
        if (!isEnabled) {
            mainScreenButtonAdd.visibility = View.INVISIBLE
            return
        }

        mainScreenButtonAdd.visibility = View.VISIBLE
    }

    override fun displayTimeEntryInList(timeEntry: TimeEntry) {
        mainScreenRecyclerView.visibility = View.VISIBLE
        mainScreenConstraintLayoutEmptyState.visibility = View.GONE
        adapter.addTimeEntry(timeEntry)
    }

    override fun updateTimeEntryInList(timeEntry: TimeEntry, index: Int) {
        adapter.updateTimeEntry(timeEntry, index)
    }

    override fun clearTimeEntries() {
        adapter.clearTimeEntries()
    }

    override fun removeTimeEntry(index: Int) {
        adapter.removeTimeEntry(index)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        hideLoadingDialog()
    }

    override fun showMessage(message: String) {
        showToast(message)
    }
}