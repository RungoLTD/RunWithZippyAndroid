package com.rungo.runwithzippy.presentation.features.main

import android.content.Context
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.esotericsoftware.spine.view.core.DisplayRenderParam
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentMainBinding
import com.rungo.runwithzippy.utils.animationModel.Zippy2
import com.rungo.runwithzippy.utils.extensions.dip2px

class MainFragment : AndroidFragmentApplication() {

    private lateinit var binding: FragmentMainBinding

    private val cfg: AndroidApplicationConfiguration by lazy { AndroidApplicationConfiguration() }
    private var zippyView: View? = null
    private var zippy: Zippy2? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setupZippy()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addZippy()
    }

    private fun setupZippy() {
        cfg.r = 8.also { cfg.a = it }.also { cfg.b = it }.also { cfg.g = it }
        zippy = Zippy2()

        val displayRenderParam = DisplayRenderParam()
        displayRenderParam.isShowBones = false
        displayRenderParam.isShowRegions = false
        displayRenderParam.isShowBoundingBoxes = false
        displayRenderParam.isShowPaths = false
        displayRenderParam.isShowPoints = false
        displayRenderParam.isShowClipping = false
        displayRenderParam.isShowPremultipliedAlpha = false
        displayRenderParam.speed = 0.3f
        displayRenderParam.scaleX = 0.3f
        displayRenderParam.scaleY = 0.3f
        displayRenderParam.defaultMix = 0.37f
        zippy?.renderParam = displayRenderParam

        if (zippyView == null) {
           zippyView = initializeForView(zippy, cfg)
        }

        if (zippyView is SurfaceView) {
            val glView = zippyView as SurfaceView
            glView.holder.setFormat(PixelFormat.TRANSLUCENT)
            glView.setZOrderMediaOverlay(true)
            glView.setZOrderOnTop(true)
        }

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

    override fun onPause() {
        super.onPause()
    }


}