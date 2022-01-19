package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.ContainerFragmentProfileBinding
import com.rungo.runwithzippy.databinding.ContainerFragmentShopBinding
import kotlinx.android.synthetic.main.activity_profile.view.*

class ContainerProfileFragment: BaseFragment() {

    private lateinit var binding: ContainerFragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ContainerFragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}