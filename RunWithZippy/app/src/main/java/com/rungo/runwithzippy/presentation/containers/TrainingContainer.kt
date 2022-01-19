package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ContainerFragmentTrainingBinding

class TrainingContainer : BaseActivity() {

    private val binding: ContainerFragmentTrainingBinding by binding(R.layout.container_fragment_training)

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
        finish()
    }
}