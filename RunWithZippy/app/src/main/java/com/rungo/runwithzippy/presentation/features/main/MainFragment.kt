package com.rungo.runwithzippy.presentation.features.main

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.rungo.runwithzippy.databinding.FragmentMainBinding
import com.rungo.runwithzippy.presentation.containers.RunningContainer
import com.rungo.runwithzippy.utils.AnimationEnum
import com.rungo.runwithzippy.utils.animationModel.Zippy
import com.rungo.runwithzippy.utils.extensions.dip2px
import com.rungo.runwithzippy.utils.extensions.getScreenWidth
import com.rungo.runwithzippy.utils.extensions.showToast

class MainFragment : AndroidFragmentApplication() {

    private lateinit var binding: FragmentMainBinding

    private val cfg: AndroidApplicationConfiguration by lazy { AndroidApplicationConfiguration() }
    private var zippyView: View? = null
    private var zippy: Zippy? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setupZippy()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.btnStart.setOnClickListener {
            val intent = Intent(requireActivity(), RunningContainer::class.java)
            startActivity(intent)
        }

        zippyView?.setOnTouchListener { view, motionEvent ->
            val action = motionEvent.action

            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                zippy?.setAnimate(AnimationEnum.PUNCH.animationName)
            }

            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addZippy()
    }

    private fun setupZippy() {
        cfg.r = 8.also { cfg.a = it }.also { cfg.b = it }.also { cfg.g = it }
        zippy = Zippy(requireContext(), 0.5f, getScreenWidth(requireContext()) / 24.0f, 0.0f)

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
            zippyView, ViewGroup.LayoutParams(getScreenWidth(requireContext()), getScreenWidth(requireContext())))
    }
}