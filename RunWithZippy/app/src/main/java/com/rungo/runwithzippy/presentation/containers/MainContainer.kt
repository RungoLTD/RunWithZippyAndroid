package com.rungo.runwithzippy.presentation.containers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ActivityMainContainerBinding
import com.rungo.runwithzippy.utils.KeepStateNavigator

class MainContainer : BaseActivity(), DrawerLayout.DrawerListener {

    private val binding: ActivityMainContainerBinding by binding(R.layout.activity_main_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
        setupNavigation()
        setupObserver()
    }

    override fun exit() {

    }

    @SuppressLint("RestrictedApi")
    private fun setupNavigation() {
        val navController = findNavController(R.id.mainContainer)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer)!!
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.mainContainer)
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.main)
        binding.navigation.setupWithNavController(navController)
    }

    private fun setupListeners() {
        binding.ivMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.drawerLayout.addDrawerListener(this)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = false
            binding.drawerLayout.closeDrawers()

            when (menuItem.itemId) {

            }
            true
        }
    }

    private fun setupObserver() {
        connectionLiveData.observe(this, Observer {
            binding.disableNetwork.visibility = if (it) View.GONE else View.VISIBLE
        })
    }

    override fun onDrawerStateChanged(newState: Int) {}

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        binding.fragmentContainer.x = slideOffset * drawerView.width;
        binding.drawerLayout.bringChildToFront(drawerView);
        binding.drawerLayout.requestLayout();
    }

    override fun onDrawerClosed(drawerView: View) {}

    override fun onDrawerOpened(drawerView: View) {}
}
