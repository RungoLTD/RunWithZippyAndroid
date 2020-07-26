package com.rungo.runwithzippy.presentation.features.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentChallengesBinding

class ChallengeFragment : BaseFragment() {

    private lateinit var binding: FragmentChallengesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_challenges, container)
        return binding.root
    }
}