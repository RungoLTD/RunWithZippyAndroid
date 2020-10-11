package com.rungo.runwithzippy.presentation.features.running

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentPrepareBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class PrepareFragment : BaseFragment() {

    private lateinit var binding: FragmentPrepareBinding

    private var timer: CountDownTimer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_prepare, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        timer = object : CountDownTimer(4000, 1000) {
            override fun onFinish() {
                findNavController().navigate(R.id.runningFragment)
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.tvCount.text = (millisUntilFinished / 1000).toString()
            }
        }

        timer?.start()
    }

    override fun setupListeners() {
        binding.switchWarm.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                findNavController().navigate(R.id.warmUpFragment)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }
}