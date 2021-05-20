package com.thirtyeight.thirtyeight.presentation.screens.auth

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.nikoloz14.myextensions.makeGone
import com.nikoloz14.myextensions.makeVisible
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ui.views.CustomToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by nikolozakhvlediani on 4/6/21.
 */
class AuthActivity : AppCompatActivity(), CustomToolbar.ToolbarEventListener {

    private lateinit var appBar: AppBarLayout
    private lateinit var customToolbar: CustomToolbar
    private lateinit var progressBar: ProgressBar

    private val viewModel by viewModel<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        appBar = findViewById(R.id.app_bar)
        progressBar = findViewById(R.id.progress_bar)
        customToolbar = findViewById(R.id.custom_toolbar)
        setSupportActionBar(customToolbar.toolbar)
        customToolbar.toolbarEventListener = this
        listenToNavigation()
    }

    private fun updateProgress(progress: Int) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, true)
        } else {
            progressBar.progress = progress
        }
    }

    private fun listenToNavigation() {
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_get_started -> {
                    updateProgress(25)
                    progressBar.makeVisible()
                }
                R.id.nav_register_step_one -> {
                    updateProgress(50)
                    progressBar.makeVisible()
                }
                R.id.nav_register_step_two -> {
                    updateProgress(75)
                    progressBar.makeVisible()
                }
                R.id.nav_register_step_last -> {
                    updateProgress(100)
                    progressBar.makeVisible()
                }
                else -> {
                    progressBar.makeGone()
                }
            }
            destination.label?.let {
                customToolbar.title = it.toString()
                appBar.makeVisible()
                showBackArrow(true)
            } ?: run {
                appBar.makeGone()
                showBackArrow(false)
            }
        }
    }

    private fun showBackArrow(show: Boolean) {
        customToolbar.setImageResourceLeft(if (show) R.drawable.ic_back_arrow else null)
    }

    override fun onLeftIconClicked() {
        findNavController(R.id.nav_host_fragment).navigateUp()
    }

    override fun onRightIconClicked() {
    }
}