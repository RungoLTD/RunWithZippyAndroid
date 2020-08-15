package com.rungo.runwithzippy.presentation.features.main

import android.content.Context
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.google.android.material.snackbar.Snackbar
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentMainBinding
import com.rungo.runwithzippy.utils.extensions.dip2px
import com.rungo.runwithzippy.utils.animationModel.Zippy

class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding

    private val cfg: AndroidApplicationConfiguration by lazy { AndroidApplicationConfiguration() }
    private var zippyView: View? = null
    private var zippy: Zippy? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = binding(layoutInflater, R.layout.fragment_main, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        setupZippy()
    }

    private fun setupZippy() {
        cfg.r = 8.also { cfg.a = it }.also { cfg.b = it }.also { cfg.g = it }

        zippy = Zippy(requireContext())

        if (zippyView == null) {
            zippyView = (requireActivity() as BaseActivity).getInitializeForView(zippy!!, cfg)
        }

        if (zippyView is SurfaceView) {
            val glView = zippyView as SurfaceView
            glView.holder.setFormat(PixelFormat.TRANSLUCENT)
            glView.setZOrderMediaOverlay(true)
            glView.setZOrderOnTop(true)
        }

        addZippy()
    }

    private fun addZippy() {
        zippyView?.parent?.let {
            (it as ViewGroup).removeView(zippyView)
        }
        binding.rlZippy.addView(
            zippyView,
            ViewGroup.LayoutParams(getScreenWidth(), dip2px(400F, requireContext()))
        )
    }

    private fun getScreenWidth(): Int {
        val windowManager = requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    override fun setupListeners() {
        binding.btnStart.setOnClickListener {

        }
    }
}