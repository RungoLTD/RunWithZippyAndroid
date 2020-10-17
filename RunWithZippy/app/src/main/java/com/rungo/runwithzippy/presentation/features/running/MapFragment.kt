package com.rungo.runwithzippy.presentation.features.running

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentMapBinding

class MapFragment : BaseFragment() {

    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_map, container)
        return binding.root
    }
}