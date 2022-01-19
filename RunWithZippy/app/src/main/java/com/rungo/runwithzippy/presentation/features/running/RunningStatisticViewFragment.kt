package com.rungo.runwithzippy.presentation.features.running

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.data.model.RunningStatisticsViewRequest
import com.rungo.runwithzippy.databinding.FragmentRunningStatisticViewBinding
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import org.koin.android.viewmodel.ext.android.getViewModel

class RunningStatisticViewFragment : BaseFragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentRunningStatisticViewBinding
    private val viewModel by lazy { getViewModel<RunningViewModel>() }
    private lateinit var mMap: GoogleMap
    private lateinit var polyLine: Polyline
    private var statisticId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = getActivity()?.intent
        statisticId = intent?.getIntExtra("statisticId", 0) ?: 0
        println("YES")
        println(statisticId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = binding(layoutInflater, R.layout.fragment_running_statistic_view, container)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.running_stat_mapGoogleFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return binding.root
//        return inflater.inflate(R.layout.fragment_runiing_statistic_view, container, false)
    }

    override fun onAfterViewCreated() {
        binding.toolbar.setNavigationOnClickListener{
            this.requireActivity().onBackPressed()
        }
        if (statisticId != 0) {
            setupEventListener(viewModel)
            if (this.isNetworkAvailable()) {
                val sharedPrefences = this.requireActivity()
                    .getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
                val access_token = sharedPrefences.getString(Constants.ACCESS_TOKEN, "").toString()
                viewModel.getStatisticView(RunningStatisticsViewRequest(access_token, statisticId))
            } else {
                Toast.makeText(
                    requireContext(),
                    "Отсутствует интернет соединение",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> {
                println("DISTANCE = "+viewModel.get_statistic.value!!.meters)
                binding.toolbar.title = viewModel.get_statistic.value!!.name
                binding.tvRunningStatDistance.text = "%.2f".format(viewModel.get_statistic.value!!.meters * 0.001) + " км"
                val hour = viewModel.get_statistic.value!!.time / 3600
                val minute = (viewModel.get_statistic.value!!.time % 3600) / 60
                val second = viewModel.get_statistic.value!!.time % 60
                if (hour == 0L)
                    binding.tvRunningStatTime.text = String.format("%02d:%02d", minute, second)
                else
                    binding.tvRunningStatTime.text = String.format(
                        "%02d:%02d:%02d",
                        hour,
                        minute,
                        second
                    )
                binding.tvRunningStatAvgPace.text = "" + viewModel.get_statistic.value!!.avgPace
                binding.tvRunningStatAvgSpeed.text = "" + viewModel.get_statistic.value!!.avgSpeed
                binding.tvRunningStatStep.text = "" + viewModel.get_statistic.value!!.paces

                if (viewModel.get_statistic.value!!.routes!!.size > 0) {
                    val options = PolylineOptions().color(Color.RED).geodesic(true)
                    for (i in 0..viewModel.get_statistic.value!!.routes!!.size - 1) {
                        options.add(
                            LatLng(
                                viewModel.get_statistic.value!!.routes!!.get(i).lat,
                                viewModel.get_statistic.value!!.routes!!.get(i).lon
                            )
                        )
                    }
                    polyLine = mMap.addPolyline(options)
                    mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                viewModel.get_statistic.value!!.routes!!.get(0).lat,
                                viewModel.get_statistic.value!!.routes!!.get(0).lon
                            ), 16F
                        )
                    )
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        println("MAPS SYNC")
        mMap = googleMap
    }

}