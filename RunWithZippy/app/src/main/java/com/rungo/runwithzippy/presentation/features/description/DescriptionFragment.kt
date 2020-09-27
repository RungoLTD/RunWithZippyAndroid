package com.rungo.runwithzippy.presentation.features.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentDescriptionBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class DescriptionFragment : BaseFragment() {

    private lateinit var binding: FragmentDescriptionBinding

    private val viewModel by lazy { getViewModel<DescriptionViewModel>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_description, container)
        return binding.root
    }

    override fun onAfterViewCreated() {

    }

    override fun setupObservers() {
        viewModel.training.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.tvProgramTitle.text = it.title?.ru
                binding.tvDescription.text = it.description?.ru
                binding.tvFull.text = it.full?.ru
            }
        })
    }
}