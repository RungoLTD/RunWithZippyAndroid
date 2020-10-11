package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ActivityRunningContainerBinding

class RunningContainer : BaseActivity() {

    private val binding: ActivityRunningContainerBinding by binding(R.layout.activity_running_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        connectionLiveData.observe(this, Observer {
            binding.disableNetwork.visibility = if (it) View.GONE else View.VISIBLE
        })
    }

    override fun exit() {

    }

    override fun onBackPressed() {
        binding.fragmentContainer.findNavController()
            .addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.warmUpFragment -> finish()
                    else -> super.onBackPressed()
                }
            }
    }
}