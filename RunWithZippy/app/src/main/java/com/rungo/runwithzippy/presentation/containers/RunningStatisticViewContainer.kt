package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ActivityLoginContainerBinding
import com.rungo.runwithzippy.databinding.ActivityRunningStatisticViewContainerBinding
import com.rungo.runwithzippy.databinding.ActivityRunningStatisticsContainerBinding

class RunningStatisticViewContainer : BaseActivity() {

    private val binding: ActivityRunningStatisticViewContainerBinding by binding(R.layout.activity_running_statistic_view_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    override fun exit() {

    }

    private fun setupObservers() {
        connectionLiveData.observe(this, Observer {
            binding.disableNetwork.visibility = if (it) View.GONE else View.VISIBLE
        })
    }
}