package com.rungo.runwithzippy.presentation.features.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.ActivityLoginContainerBinding
import com.rungo.runwithzippy.databinding.FragmentDescriptionBinding
import com.rungo.runwithzippy.utils.EnumTrainingName
import org.koin.android.viewmodel.ext.android.getViewModel

class DescriptionActivity : BaseActivity() {

    private val binding: FragmentDescriptionBinding by binding(R.layout.fragment_description)

    private val viewModel by lazy { getViewModel<DescriptionViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.training.observe(this, Observer {
            it?.let {
                binding.tvTrainingName.text = it.title?.ru
                binding.tvDescription.text = it.description?.ru
                binding.tvFull.text = it.full?.ru

                binding.ivDescription.apply {
                    background = when(it.title?.en) {
                        EnumTrainingName.Beginning.name -> {
                            ContextCompat.getDrawable(context, R.drawable.training_1)
                        }
                        EnumTrainingName.Children.name -> {
                            ContextCompat.getDrawable(context, R.drawable.training_2)
                        }
                        EnumTrainingName.Slimming.name -> {
                            ContextCompat.getDrawable(context, R.drawable.training_3)
                        }
                        else -> {
                            ContextCompat.getDrawable(context, R.drawable.training_4)
                        }
                    }
                }
            }
        })
    }

    private fun setupListeners() {
        binding.btnStart.setOnClickListener {

        }
    }

    override fun exit() {

    }
}