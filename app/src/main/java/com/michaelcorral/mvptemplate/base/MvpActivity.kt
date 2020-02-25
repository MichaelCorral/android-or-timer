package com.michaelcorral.mvptemplate.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.michaelcorral.mvptemplate.R
import org.koin.ext.getFullName
import timber.log.Timber

abstract class MvpActivity : AppCompatActivity() {
    private var builder: AlertDialog.Builder? = null
    private var alertDialog: AlertDialog? = null

    protected abstract fun getActivityLayout(): Int

    protected abstract fun getActivityPresenter(): BasePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getActivityLayout())

        onActivityReady(savedInstanceState, intent)
        Timber.i("onCreate ${this::class.qualifiedName}")
    }

    open fun onActivityReady(savedInstanceState: Bundle?, intent: Intent) {
        // To be used by child activities
    }

    override fun onStart() {
        super.onStart()

        Timber.i("onStart ${this::class.qualifiedName}")
    }

    override fun onResume() {
        super.onResume()

        Timber.i("onResume ${this::class.qualifiedName}")
    }

    fun showSnackBar(layout: View, message: String, duration: Int) {
        val snackBar = Snackbar.make(layout, message, duration)
        snackBar.show()
    }

    fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    fun showLoadingDialog() {
        builder = AlertDialog.Builder(this)
        builder?.setView(R.layout.progressbar_layout)
        alertDialog = builder?.create()
        alertDialog?.show()
    }

    fun hideLoadingDialog() {
        alertDialog?.dismiss()
    }

    override fun onPause() {
        super.onPause()

        Timber.i("onPause ${this::class.qualifiedName}")
    }

    override fun onStop() {
        super.onStop()

        Timber.i("onStop ${this::class.qualifiedName}")
    }

    override fun onDestroy() {
        super.onDestroy()

        getActivityPresenter().detachView()
        Timber.i("onDestroy ${this::class.qualifiedName}")
    }
}