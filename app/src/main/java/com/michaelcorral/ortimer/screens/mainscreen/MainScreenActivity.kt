package com.michaelcorral.ortimer.screens.mainscreen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
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
import com.michaelcorral.ortimer.services.VolumeService
import com.michaelcorral.ortimer.services.VolumeServiceListener
import kotlinx.android.synthetic.main.mainscreen_activity.*
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class MainScreenActivity : OrTimerActivity(), MainScreenContract.View, VolumeServiceListener {

    private val presenter: MainScreenContract.Presenter by currentScope.inject { parametersOf(this) }

    private var isServiceBounded = false
    private lateinit var volumeServiceIntent: Intent
    private lateinit var volumeService: VolumeService
    private lateinit var serviceConnection: ServiceConnection

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

    override fun togglePlayButton() {
        mainScreenTextViewStartSession.text = getString(R.string.mainscreen_in_session)
        mainScreenButtonPlay.background = getDrawable(R.drawable.all_shape_stop_button)
    }

    override fun toggleStopButton() {
        mainScreenTextViewStartSession.text = getString(R.string.mainscreen_start_a_session)
        mainScreenButtonPlay.background = getDrawable(R.drawable.all_shape_play_button)
    }

    override fun startSession() {
        initializeServiceConnection()
        initializeVolumeService()
    }

    private fun initializeServiceConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val localBinder = service as VolumeService.LocalBinder
                volumeService = localBinder.service
                isServiceBounded = true
                volumeService.setCallback(this@MainScreenActivity)
            }

            override fun onServiceDisconnected(name: ComponentName) {
                stopSession()
            }
        }
    }

    private fun initializeVolumeService() {
        volumeServiceIntent = Intent(this, VolumeService::class.java)
        val startIntent = Intent(this, VolumeService::class.java)
        startIntent.action = "START"
        startService(startIntent)
        applicationContext.bindService(
            volumeServiceIntent,
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun stopSession() {
        val stopIntent = Intent(this, VolumeService::class.java)
        stopIntent.action = "STOP"
        Timber.d("Stop Session")
        if (isServiceBounded) {
            Timber.d("Stop Session bounded")
            applicationContext.unbindService(serviceConnection)
            isServiceBounded = false
            stopService(volumeServiceIntent)
            startService(stopIntent)
        } else {
            Timber.d("Stop Service")
            stopService(Intent(this, VolumeService::class.java))
            startService(stopIntent)
        }
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

    override fun onDestroy() {
        super.onDestroy()

        //TODO REFACTOR THIS SHIT
        if (isServiceBounded) {
            Timber.d("Stop Session bounded")
            applicationContext.unbindService(serviceConnection)
            isServiceBounded = false
        }
    }
}