package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.ContainerFragmentChallengeBinding

class ContainerChallengeFragment: BaseFragment() {

    private lateinit var binding: ContainerFragmentChallengeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ContainerFragmentChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }
}