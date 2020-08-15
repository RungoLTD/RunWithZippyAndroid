package com.rungo.runwithzippy.presentation.features.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentTrainingBinding

class TrainingFragment : BaseFragment() {

    private lateinit var binding: FragmentTrainingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_training, container)
        return binding.root
    }
}