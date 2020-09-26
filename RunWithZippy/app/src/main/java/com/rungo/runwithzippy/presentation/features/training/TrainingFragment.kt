package com.rungo.runwithzippy.presentation.features.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentTrainingBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class TrainingFragment : BaseFragment() {

    private lateinit var binding: FragmentTrainingBinding

    private val viewModel by lazy { getViewModel<TrainingViewModel>() }

    private val adapterRecommended by lazy { TrainingAdapter() }

    private val adapterPopular by lazy { TrainingAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_training, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        setupEventListener(viewModel)

        binding.rvRecommended.adapter = adapterRecommended
        binding.rvPopular.adapter = adapterPopular
    }

    override fun setupObservers() {
        viewModel.trainings.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                adapterRecommended.setList(list[0])
                adapterPopular.setList(list[1] + list[2])
            }
        })
    }
}