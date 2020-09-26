package com.rungo.runwithzippy.presentation.features.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.data.model.AuthWithEmail
import com.rungo.runwithzippy.databinding.FragmentChallengesBinding
import com.rungo.runwithzippy.presentation.containers.LoginContainer
import com.rungo.runwithzippy.presentation.containers.MainContainer
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import com.rungo.runwithzippy.utils.extensions.showToast
import com.rungo.runwithzippy.utils.extensions.text
import org.koin.android.viewmodel.ext.android.getViewModel

class ChallengeFragment : BaseFragment() {

    private lateinit var binding: FragmentChallengesBinding

    private val adapter: ChallengeAdapter by lazy { ChallengeAdapter() }

    private val viewModel by lazy { getViewModel<ChallengeViewModel>() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_challenges, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        setupEventListener(viewModel)

        if ((requireActivity() as MainContainer).isNetworkAvailable()) {
            viewModel.getChallenges()
        } else {
            showToast("Отсутствует интернет соединение")
        }


        binding.rvChallenges.adapter = adapter
    }

    override fun setupObservers() {
        viewModel.challenges.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.setList(it)
            }
        })

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> { }
            EventEnums.FAIL -> {
                showToast("Не удалось получить данные")
            }
            EventEnums.OTHER -> { }
        }
    }
}