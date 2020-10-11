package com.rungo.runwithzippy.presentation.features.running

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentRunningBinding

class RunningFragment : BaseFragment() {

    private lateinit var binding: FragmentRunningBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_running, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        binding.tvCaloriesCount.text = "0"
    }
}