package com.rungo.runwithzippy.presentation.features.running

import android.graphics.PixelFormat
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.rungo.runwithzippy.databinding.FragmentWarmUpBinding
import com.rungo.runwithzippy.utils.animationModel.Zippy
import com.rungo.runwithzippy.utils.extensions.dip2px
import com.rungo.runwithzippy.utils.extensions.getScreenWidth
import java.util.*
import java.util.concurrent.TimeUnit

class WarmUpFragment : AndroidFragmentApplication() {

    private lateinit var binding: FragmentWarmUpBinding

    private val cfg: AndroidApplicationConfiguration by lazy { AndroidApplicationConfiguration() }
    private var zippyView: View? = null
    private var zippy: Zippy? = null

    private var time: Long? = null
    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWarmUpBinding.inflate(inflater, container, false)
        setupZippy()
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addZippy()

        setTimer(31000)
    }

    private fun setupListeners() {
        binding.btnUpSec.setOnClickListener {
            timer?.let {
                it.cancel()
            }

            time?.plus(20000)?.let { remainTime ->
                setTimer(remainTime)
            }
        }
    }

    private fun setupZippy() {
        cfg.r = 8.also { cfg.a = it }.also { cfg.b = it }.also { cfg.g = it }
        zippy = Zippy(requireContext(), 0.4f, (getScreenWidth(requireContext()) / 3).toFloat())

        if (zippyView == null) {
            zippyView = initializeForView(zippy, cfg)
        }

        if (zippyView is SurfaceView) {
            val glView = zippyView as SurfaceView
            glView.holder.setFormat(PixelFormat.TRANSLUCENT)
            glView.setZOrderMediaOverlay(false)
            glView.setZOrderOnTop(true)
        }
    }

    private fun addZippy() {
        zippyView?.parent?.let {
            (it as ViewGroup).removeView(zippyView)
        }
        binding.rlZippy.addView(
            zippyView,
            ViewGroup.LayoutParams(getScreenWidth(requireContext()), dip2px(400F, requireContext()))
        )
    }

    private fun setTimer(seconds: Long) {
        timer = object : CountDownTimer(seconds, 1000) {
            override fun onFinish() {
                //findNavController().navigate(screen)
            }

            override fun onTick(millisUntilFinished: Long) {
                time = millisUntilFinished

                val text: String = String.format(
                    Locale.getDefault(), "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                )

                binding.tvTime.text = text
            }
        }

        timer?.start()
    }
}