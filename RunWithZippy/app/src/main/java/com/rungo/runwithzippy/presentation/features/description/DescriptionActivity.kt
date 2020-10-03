package com.rungo.runwithzippy.presentation.features.description

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ActivityDescriptionBinding
import com.rungo.runwithzippy.presentation.features.creationTraining.CreationTrainingActivity
import com.rungo.runwithzippy.utils.EnumTrainingName
import org.koin.android.viewmodel.ext.android.getViewModel

class DescriptionActivity : BaseActivity() {

    private val binding: ActivityDescriptionBinding by binding(R.layout.activity_description)

    private val viewModel by lazy { getViewModel<DescriptionViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
        setupListeners()
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
            val intent = Intent(this, CreationTrainingActivity::class.java)
            startActivity(intent)
        }
    }

    override fun exit() {

    }
}