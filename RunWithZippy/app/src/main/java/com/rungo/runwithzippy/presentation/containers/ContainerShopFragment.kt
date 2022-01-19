package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.ContainerFragmentShopBinding

class ContainerShopFragment: BaseFragment() {

    private lateinit var binding: ContainerFragmentShopBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ContainerFragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }
}