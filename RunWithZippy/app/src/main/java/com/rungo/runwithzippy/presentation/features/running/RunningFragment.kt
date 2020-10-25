package com.rungo.runwithzippy.presentation.features.running

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.here.android.mpa.common.GeoCoordinate
import com.here.android.mpa.common.MapSettings
import com.here.android.mpa.common.OnEngineInitListener
import com.here.android.mpa.mapping.AndroidXMapFragment
import com.here.android.mpa.mapping.Map
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.databinding.FragmentRunningBinding
import com.rungo.runwithzippy.utils.extensions.showToast
import timber.log.Timber
import java.io.File


class RunningFragment : BaseFragment() {

    private lateinit var binding: FragmentRunningBinding

    private var timeWhenStopped: Long = 0

    private var isClicked: Boolean = true

    private var map: Map? = null
    private lateinit var mapFragment: AndroidXMapFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(layoutInflater, R.layout.fragment_running, container)
        return binding.root
    }

    override fun onAfterViewCreated() {
        binding.tvCaloriesCount.text = "0"
        binding.tvTimeCount.start()

        MapSettings.setDiskCacheRootPath(
            requireContext().getExternalFilesDir(null).toString() + File.separator + ".here-maps"
        )

        initMap()
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

    private fun initMap() {
        mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as AndroidXMapFragment

        mapFragment.init {
            if (it == OnEngineInitListener.Error.NONE) {
                map = mapFragment.map

                map?.let { map ->
                    map.setCenter(GeoCoordinate(62.028098, 129.732555), Map.Animation.NONE)
                    map.zoomLevel = (map.maxZoomLevel + map.minZoomLevel) / 2
                }
            } else {
                showToast(getString(R.string.map_error))

                Timber.d("Map error ${it.name} ${it.details}")
            }
        }
    }
}