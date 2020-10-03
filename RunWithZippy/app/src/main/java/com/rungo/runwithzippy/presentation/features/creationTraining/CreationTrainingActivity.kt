package com.rungo.runwithzippy.presentation.features.creationTraining

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ActivityCreationTrainingBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class CreationTrainingActivity : BaseActivity() {

    private val binding: ActivityCreationTrainingBinding by binding(R.layout.activity_creation_training)

    private val adapter by lazy { CreationTrainingAdapter() }

    private val viewModel by lazy { getViewModel<CreationTrainingViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vpFragment.adapter = adapter

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.options.observe(this, Observer {
            it?.let {
                adapter.setList(it)
            }
        })
    }

    private fun setupListeners() {
        adapter.setItemClickListener {
            moveNext()
        }
    }

    override fun exit() {

    }

    private fun moveNext() {
        binding.vpFragment.currentItem = binding.vpFragment.currentItem + 1
    }

    private fun movePrevious() {
        binding.vpFragment.currentItem = binding.vpFragment.currentItem - 1
    }

    override fun onBackPressed() {
        if (binding.vpFragment.currentItem != 0) {
            movePrevious()
        } else {
            super.onBackPressed()
        }
    }
}