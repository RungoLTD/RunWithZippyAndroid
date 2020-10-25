package com.rungo.runwithzippy.presentation.features.creationTraining

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ActivityCreationTrainingBinding
import com.rungo.runwithzippy.databinding.BottomDialogBuyingBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class CreationTrainingActivity : BaseActivity() {

    private val binding: ActivityCreationTrainingBinding by binding(R.layout.activity_creation_training)

    private val adapter by lazy { CreationTrainingAdapter() }

    private val viewModel by lazy { getViewModel<CreationTrainingViewModel>() }

    private val bottomSheetDialog by lazy { BottomSheetDialog(this) }
    private val dialogBinding by lazy { BottomDialogBuyingBinding.inflate(LayoutInflater.from(this), null, false) }
    private var listCount: Int = -1

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
                listCount = it.size
            }
        })
    }

    private fun setupListeners() {
        adapter.setItemClickListener {
            if (binding.vpFragment.currentItem == listCount - 1) {
                showBottomDialog()
            } else {
                moveNext()
            }
        }

        adapter.setOnBackClickListener {
            onBackPressed()
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

    private fun showBottomDialog() {
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
    }
}