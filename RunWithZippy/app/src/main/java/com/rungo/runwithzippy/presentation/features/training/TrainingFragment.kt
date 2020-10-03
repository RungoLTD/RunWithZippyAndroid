package com.rungo.runwithzippy.presentation.features.training

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentTrainingBinding
import com.rungo.runwithzippy.presentation.containers.MainContainer
import com.rungo.runwithzippy.presentation.features.description.DescriptionActivity
import com.rungo.runwithzippy.presentation.features.description.DescriptionViewModel
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import com.rungo.runwithzippy.utils.extensions.showToast
import org.koin.android.viewmodel.ext.android.getViewModel

class TrainingFragment : BaseFragment() {

    private lateinit var binding: FragmentTrainingBinding

    private val viewModel by lazy { getViewModel<TrainingViewModel>() }

    private val adapterRecommended by lazy { TrainingAdapter() }

    private val adapterPopular by lazy { TrainingAdapter() }

    private val adapterMarathon by lazy { MarathonAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_training, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        setupEventListener(viewModel)

        binding.rvRecommended.adapter = adapterRecommended
        binding.rvPopular.adapter = adapterPopular
        binding.vpMarathon.adapter = adapterMarathon
        binding.tab.setupWithViewPager(binding.vpMarathon)

        if ((requireActivity() as MainContainer).isNetworkAvailable()) {
            viewModel.getTrainings()
        } else {
            showToast("Отсутствует интернет соединение")
        }
    }

    override fun setupObservers() {
        viewModel.trainings.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                binding.apply {
                    tvRecommended.visibility = View.VISIBLE
                    tvPopular.visibility = View.VISIBLE
                    rlMarathon.visibility = View.VISIBLE
                }

                adapterRecommended.setList(list[0])
                adapterPopular.setList(list[1] + list[2])
                adapterMarathon.setList(list[3])
            }
        })

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun setupListeners() {
        adapterRecommended.setItemClickListener {
            viewModel.training.value = it
            val intent = Intent(requireActivity(), DescriptionActivity::class.java)
            startActivity(intent)
        }

        adapterPopular.setItemClickListener {
            viewModel.training.value = it
            val intent = Intent(requireActivity(), DescriptionActivity::class.java)
            startActivity(intent)
        }

        adapterMarathon.setItemClickListener {
            viewModel.training.value = it
            val intent = Intent(requireActivity(), DescriptionActivity::class.java)
            startActivity(intent)
        }
    }
}