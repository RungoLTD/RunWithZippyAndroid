package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.ContainerFragmentMainBinding

class ContainerMainFragment: BaseFragment() {

    private lateinit var binding: ContainerFragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ContainerFragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
}