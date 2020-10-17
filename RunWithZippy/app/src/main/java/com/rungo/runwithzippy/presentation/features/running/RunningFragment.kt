package com.rungo.runwithzippy.presentation.features.running

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentRunningBinding


class RunningFragment : BaseFragment() {

    private lateinit var binding: FragmentRunningBinding

    private var timeWhenStopped: Long = 0

    private var isClicked: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_running, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        binding.tvCaloriesCount.text = "0"

        binding.tvTimeCount.start()
    }

    override fun setupListeners() {
        binding.ivPause.setOnClickListener {
            binding.llPause.visibility = View.VISIBLE
            binding.rlPlay.visibility = View.GONE

            timeWhenStopped = binding.tvTimeCount.base - SystemClock.elapsedRealtime()
            binding.tvTimeCount.stop()
        }

        binding.ivPlay.setOnClickListener {
            binding.rlPlay.visibility = View.VISIBLE
            binding.llPause.visibility = View.GONE

            binding.tvTimeCount.base = SystemClock.elapsedRealtime() + timeWhenStopped;
            binding.tvTimeCount.start();
        }

        binding.ivPosition.setOnClickListener {
            isClicked = if (isClicked) {
                binding.rlMap.visibility = View.VISIBLE
                false
            } else {
                binding.rlMap.visibility = View.GONE
                true
            }
        }
    }

    fun slideUp(view: View) {
        val animate = TranslateAnimation(0f, 0f, binding.rlMap.height.toFloat(), 0f)
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
        binding.rlMap.visibility = View.GONE
    }

    fun slideDown(view: View) {
        val animate = TranslateAnimation(0f, 0f, 0f, binding.rlMap.height.toFloat())
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
        binding.rlMap.visibility = View.VISIBLE
    }
}