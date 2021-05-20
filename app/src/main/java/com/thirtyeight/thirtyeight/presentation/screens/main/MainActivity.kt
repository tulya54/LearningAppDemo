package com.thirtyeight.thirtyeight.presentation.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thirtyeight.thirtyeight.R
import com.thirtyeight.thirtyeight.presentation.ui.views.CustomToolbar
import com.thirtyeight.thirtyeight.util.setupWithNavController

/**
 * Created by nikolozakhvlediani on 4/19/21.
 */
class MainActivity : AppCompatActivity(), CustomToolbar.ToolbarEventListener {

    private lateinit var appBar: AppBarLayout
    private lateinit var customToolbar: CustomToolbar

    val navTabItems = hashSetOf(R.id.nav_discover, R.id.nav_my_topics, R.id.nav_edit_profile)
    val navIconsMap = hashMapOf(
            R.id.nav_edit_profile to R.drawable.ic_settings
    )

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appBar = findViewById(R.id.app_bar)
        customToolbar = findViewById(R.id.custom_toolbar)
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
        navView.itemIconTintList = null
        setupBottomNavigationBar(navView)
        customToolbar.toolbarEventListener = this
    }

    private fun setupBottomNavigationBar(bottomNavigationView: BottomNavigationView) {
        val navGraphIds = listOf(R.navigation.nav_discover_graph, R.navigation.nav_my_topics_graph, R.navigation.nav_profile_graph)
        bottomNavigationView.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_fragment,
                intent = intent
        ).observe(this) {
            navController = it
            it?.addOnDestinationChangedListener { controller, destination, arguments ->
                showBackButton(destination.id)
                customToolbar.title = destination.label.toString()
                displayIcon(destination.id)
            }
        }
    }

    private fun showBackButton(id: Int) {
        val show = !navTabItems.contains(id)
        customToolbar.setImageResourceLeft(if (show) R.drawable.ic_back_arrow else null)
    }

    private fun displayIcon(id: Int) {
        navIconsMap[id]?.let {
            customToolbar.setImageResourceRight(it)
        } ?: customToolbar.setImageResourceRight(null)
    }

    override fun onLeftIconClicked() {
        navController?.navigateUp()
    }

    override fun onRightIconClicked() {
        try {
            (supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.get(0) as? CustomToolbar.RightIconClickEventListener)?.onRightIconClicked()
        } catch (ignored: Exception) {
        }
    }
}