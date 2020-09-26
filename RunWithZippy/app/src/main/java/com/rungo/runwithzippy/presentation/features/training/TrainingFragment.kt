package com.rungo.runwithzippy.presentation.features.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentTrainingBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class TrainingFragment : BaseFragment() {

    private lateinit var binding: FragmentTrainingBinding

    private val viewModel by lazy { getViewModel<TrainingViewModel>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_training, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        setupEventListener(viewModel)


    }
}