package com.rungo.runwithzippy.presentation.features.running

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentPrepareBinding

class RunningFragment : BaseFragment() {

    private lateinit var binding: FragmentPrepareBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_prepare, container)
        return binding.root
    }
}