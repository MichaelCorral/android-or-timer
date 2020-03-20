package com.michaelcorral.ortimer.screens.mainscreen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.michaelcorral.ortimer.R
import com.michaelcorral.ortimer.base.BasePresenter
import com.michaelcorral.ortimer.base.OrTimerActivity
import com.michaelcorral.ortimer.data.local.TimeEntry
import com.michaelcorral.ortimer.services.VolumeService
import com.michaelcorral.ortimer.services.VolumeServiceListener
import kotlinx.android.synthetic.main.mainscreen_activity.*
import org.koin.androidx.scope.currentScope
import org.koin.core.parameter.parametersOf

class MainScreenActivity : OrTimerActivity(), MainScreenContract.View, VolumeServiceListener {

    private val presenter: MainScreenContract.Presenter by currentScope.inject { parametersOf(this) }

    private var isServiceBounded = false

    private lateinit var volumeServiceIntent: Intent
    private lateinit var volumeService: VolumeService
    private lateinit var serviceConnection: ServiceConnection

    private val adapter =
        MainScreenAdapter { timeEntryToBeEdited -> onTimeEntryClicked(timeEntryToBeEdited) }

    private lateinit var mainScreenDialogEditTimeEntry: MainScreenCustomDialogEditTimeEntry
    private lateinit var mainScreenDialogAddTimeEntry: MainScreenCustomDialogAddTimeEntry

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
        initializePlayButton()
        initializeAddButton()
        initializeRecyclerView()
    }

    private fun initializePlayButton() {
        mainScreenButtonPlay.setOnClickListener {
            presenter.onPlayButtonClicked()
        }
    }

    private fun initializeAddButton() {
        mainScreenButtonAdd.setOnClickListener {
            mainScreenDialogAddTimeEntry = MainScreenCustomDialogAddTimeEntry(
                context = this,
                styles = R.style.customDialogStyle,
                onSaveClicked = { timeEntry -> onSaveClicked(timeEntry) }
            )
            mainScreenDialogAddTimeEntry.show()
        }
    }

    private fun initializeRecyclerView() {
        mainScreenRecyclerView.layoutManager = LinearLayoutManager(this)
        mainScreenRecyclerView.setHasFixedSize(true)
        mainScreenRecyclerView.adapter = adapter
    }

    override fun displayTimeEntries(timeEntries: List<TimeEntry>) {
        mainScreenRecyclerView.visibility = View.VISIBLE
        mainScreenConstraintLayoutEmptyState.visibility = View.GONE
        adapter.addTimeEntries(timeEntries)
    }

    private fun onTimeEntryClicked(timeEntryToBeEdited: EditedTimeEntry) {
        mainScreenDialogEditTimeEntry = MainScreenCustomDialogEditTimeEntry(
            context = this,
            styles = R.style.customDialogStyle,
            timeEntryToBeEdited = timeEntryToBeEdited,
            onSaveClicked = { editedTimeEntry -> onSaveClicked(editedTimeEntry) }
        )

        mainScreenDialogEditTimeEntry.show()
    }

    private fun onSaveClicked(editedTimeEntry: EditedTimeEntry) {
        mainScreenDialogEditTimeEntry.dismiss()
        presenter.editTimeEntry(editedTimeEntry)
    }

    private fun onSaveClicked(timeEntry: TimeEntry) {
        mainScreenDialogAddTimeEntry.dismiss()
        presenter.saveTimeEntry(timeEntry)
    }

    //TODO: Make this a separate object
    override fun startSession() {
        initializeServiceConnection()

        volumeServiceIntent = Intent(this, VolumeService::class.java)
        startService(volumeServiceIntent)
        applicationContext.bindService(
            volumeServiceIntent,
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
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

    override fun stopSession() {
        if (isServiceBounded) {
            stopService(volumeServiceIntent)
            applicationContext.unbindService(serviceConnection)
            isServiceBounded = false
        }
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

    override fun addTimeEntry(timeEntry: TimeEntry) {
        mainScreenRecyclerView.visibility = View.VISIBLE
        mainScreenConstraintLayoutEmptyState.visibility = View.GONE
        adapter.addTimeEntry(timeEntry)
    }

    override fun updateTimeEntry(timeEntry: TimeEntry, index: Int) {
        adapter.updateTimeEntry(timeEntry, index)
    }

    override fun clearTimeEntries() {
        adapter.clearTimeEntries()
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