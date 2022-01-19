package com.rungo.runwithzippy.presentation.features.running

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.SystemClock
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.data.model.LocationModel
import com.rungo.runwithzippy.data.model.RunningRequest
import com.rungo.runwithzippy.databinding.FragmentRunningBinding
import com.rungo.runwithzippy.presentation.features.config.InitialConfigModel
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.PermissionUtils
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import com.rungo.runwithzippy.utils.extensions.showToast
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber


class RunningFragment : BaseFragment(), LocationListener, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnPolylineClickListener {

    private lateinit var binding: FragmentRunningBinding

    private var timeWhenStopped: Long = 0

    private val viewModel by lazy { getViewModel<RunningViewModel>() }
    private val initialConfigModel by lazy { getViewModel<InitialConfigModel>() }
    private var isClicked: Boolean = true
    private lateinit var mMap: GoogleMap
    private var permissionDenied = false
    private var locationManager: LocationManager? = null
    private var calculatedDistance: Long = 0
    private var runningTimeSecond: Long = 0
    private var avgSpeed: Double = 0.0
    private var maxSpeed: Double = 0.0
    private var avgPace: Double = 0.0
    private var kkal: Int = 0
    private lateinit var polyLine: Polyline
    private var firstLoc: Boolean = true
    private val points: MutableList<LocationModel> = ArrayList()
    private val arraySpeed: MutableList<Float> = ArrayList()
//    private var map: Map? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(layoutInflater, R.layout.fragment_running, container)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapGoogleFragment) as SupportMapFragment?
        println("MAPS 2 == " + mapFragment)
        locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager?
        mapFragment?.getMapAsync(this)
        initFragment()
        return binding.root
    }

    private fun initFragment() {
        binding.ivMusic.setOnClickListener {
            val intent =
                requireContext().packageManager.getLaunchIntentForPackage("com.spotify.music")
            startActivity(intent)
        }
        binding.ivPause.setOnClickListener {
            locationManager?.removeUpdates(this)
            mMap.isMyLocationEnabled = false
            binding.tvTimeCount.stop()
            println("TIME = second" + runningTimeSecond)
        }
        binding.ivPlay.setOnClickListener {
            ivPlay()
        }
        binding.ivStop.setOnClickListener {
            if(!points.isEmpty()) {
                binding.tvTimeCount.stop()
                locationManager?.removeUpdates(this)
                mMap.isMyLocationEnabled = false
                println("TIME = " + binding.tvTimeCount.base)
                println("TIME = second" + runningTimeSecond)
                if (this.isNetworkAvailable()) {
                    val sharedPrefences = this.requireActivity()
                        .getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
                    val access_token =
                        sharedPrefences.getString(Constants.ACCESS_TOKEN, "").toString()
                    println("POLYLINE")
                    println("POINT")
                    println(points)
                    println("POINT 2")
//                println(polyLine.points)
                    viewModel.statisticsAdd(
                        RunningRequest(
                            access_token,
                            calculatedDistance,
                            runningTimeSecond,
                            maxSpeed,
                            avgSpeed,
                            avgPace,
                            points,
                            0.0
                        )
                    )
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Отсутствует интернет соединение",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                alertNotRun()
            }
//TODO вот это надо добавить            polyLine.points
            //            val intent = Intent(requireActivity(), RunningFinishActivity::class.java)
//            intent.putExtra("time", binding.tvTimeCount)
//            intent.putExtra("distance", calculatedDistance)
//            startActivity(intent)
        }
    }

    override fun onAfterViewCreated() {
        binding.tvCaloriesCount.text = "0"
        binding.tvTimeCount.start()
        setupEventListener(viewModel)
    }

    override fun setupListeners() {
        binding.tvTimeCount.setOnChronometerTickListener { cArg ->
            runningTimeSecond = (SystemClock.elapsedRealtime() - cArg.base) / 1000
        }
        binding.ivPause.setOnClickListener {
            binding.llPause.visibility = View.VISIBLE
            binding.rlPlay.visibility = View.GONE
            timeWhenStopped = binding.tvTimeCount.base - SystemClock.elapsedRealtime()
            locationManager?.removeUpdates(this)
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap;
        println("YAAAAA")
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        googleMap.setOnPolylineClickListener(this)

        if (!isLocationEnabled(requireContext()))
            showLocationIsDisabledAlert()
        else
            enableMyLocation()
//        initMap()
    }

    private fun enableMyLocation() {
        if (!::mMap.isInitialized) return
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            try {
                // Request location updates
                locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 1f, this)
//                locationManager?.removeUpdates(this)
                println("SUCCESS LOCATION")
            } catch (ex: SecurityException) {
                println("ERROR LOCATION")
//                Log.d("myTag", "Security Exception, no location available")
            }
//            val intent = Intent(context, GpsTrackerService::class.java)
//            context?.startService(intent)
//            startService(GpsTrackerService.getIntent(requireContext()))
        } else {
//             Permission to access the location is missing. Show rationale and request permission
            showToast("Включи GPS")
            println("Включи GPS")
//            PermissionUtils.requestPermission(requireActivity(), LOCATION_PERMISSION_REQUEST_CODE,
//                Manifest.permission.ACCESS_FINE_LOCATION, true
//            )
        }
    }

    private fun alertNotRun(){
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage("Вы не начали бегать. Бегите")
        alertDialogBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
            ivPlay()
        }
        alertDialogBuilder.show()
    }

    private fun ivPlay(){
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            try {
                // Request location updates
                locationManager?.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    1f,
                    this
                )
                println("SUCCESS LOCATION")
            } catch (ex: SecurityException) {
                println("ERROR LOCATION")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        if (PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }

    private fun showLocationIsDisabledAlert() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage("Чтобы продолжить, включите на устройстве геолокацию Google")
        alertDialogBuilder.setPositiveButton("Включить",
            DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            })
        alertDialogBuilder.show()
    }

    private fun isLocationEnabled(mContext: Context): Boolean {
        val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
//        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

//    override fun onResumeFragments() {
//        super.onResumeFragments()
//        if (permissionDenied) {
//            // Permission was not granted, display error dialog.
//            showMissingPermissionError()
//            permissionDenied = false
//        }
//    }

    private fun showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(
            childFragmentManager,
            "dialog"
        )
    }


    override fun onMyLocationButtonClick(): Boolean {
        println("MyLocation button clicked")
//        showToast("MyLocation button clicked")
//        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        showToast("Current location:\n$location")
//        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG).show()
        println("GPS == " + location.latitude + " === " + location.longitude)
//        DistanceTracker.totalDistance += calculatedDistance
        println("DIStanCE = " + calculatedDistance)
        println("SPeed = " + location.speed)
        val latLng = LatLng(location.latitude, location.longitude)
        if (firstLoc) {
            polyLine = mMap.addPolyline(PolylineOptions().color(Color.RED).add(latLng))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F))
            firstLoc = false;
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F))
            println("POL == " + polyLine.points)
            val newLine = mMap.addPolyline(
                PolylineOptions().color(Color.RED).addAll(
                    polyLine.points
                ).add(latLng).width(10f)
            )
            val loc2 = Location("")
            loc2.latitude = polyLine.points.get(polyLine.points.size - 1).latitude
            loc2.longitude = polyLine.points.get(polyLine.points.size - 1).longitude
            val distanceInMeters = loc2.distanceTo(location).toLong()
            calculatedDistance += distanceInMeters
            polyLine.remove()
            polyLine = newLine
        }
        println("DISTANCE == " + calculatedDistance)
        binding.tvCount.setText("%.2f".format(calculatedDistance * 0.001))
//        mMap.addMarker(
//            MarkerOptions()
//                .position(LatLng(location.latitude, location.longitude))
//                .title("Я")
//        )
        mMap.moveCamera(
            CameraUpdateFactory.newLatLng(
                LatLng(
                    location.latitude,
                    location.longitude
                )
            )
        )
    }

    override fun onLocationChanged(location: Location) {
        showToast("Current location 2:\n$location")
        println("GPS 2 == " + location.latitude + " === " + location.longitude)
        val latLng = LatLng(location.latitude, location.longitude)
        if (firstLoc) {
            polyLine = mMap.addPolyline(PolylineOptions().color(Color.RED).add(latLng))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F))
            firstLoc = false;
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F))
            println("POL 2 == " + polyLine.points)
            val loc2 = Location("")
            loc2.latitude = polyLine.points.get(polyLine.points.size - 1).latitude
            loc2.longitude = polyLine.points.get(polyLine.points.size - 1).longitude
            val distanceInMeters = loc2.distanceTo(location).toLong()
            calculatedDistance += distanceInMeters
            val newLine = mMap.addPolyline(
                PolylineOptions().color(Color.RED).addAll(
                    polyLine.points
                ).add(latLng).width(10f)
            )
            polyLine.remove()
            polyLine = newLine
        }
        println("index = "+polyLine.points.size)
        points.add(LocationModel(location.latitude, location.longitude))
        println("POINTS")
        println(points)
        println("DISTANCE 2 == " + calculatedDistance)

        if(location.speed != 0.0f) {
            arraySpeed.add(location.speed)
            var allSpeed = 0f
            for (speed in arraySpeed){
                allSpeed += speed
            }
            if(maxSpeed < location.speed){
                maxSpeed = location.speed.toDouble()
            }
            println("all SPeed = " + allSpeed)
            println("size = " + arraySpeed.size)
            avgSpeed = (allSpeed/arraySpeed.size).toDouble()
        }

        avgPace = (runningTimeSecond/calculatedDistance).toDouble()

        binding.tvTempCount.text = "%.2f".format(avgPace) + " с/м"
        kkal = (calculatedDistance * 0.001 * initialConfigModel.initialConfig.value!!.user.weight).toInt()
        println("SPeed = " + location.speed) //м/с
        binding.tvCount.text = "%.2f".format(calculatedDistance * 0.001)
        binding.tvCaloriesCount.text = ""+kkal
//        mMap.addMarker(
//            MarkerOptions()
//                .position(LatLng(location.latitude, location.longitude))
//                .title("ТЫ")
//        )
        mMap.moveCamera(
            CameraUpdateFactory.newLatLng(
                LatLng(
                    location.latitude,
                    location.longitude
                )
            )
        )
    }

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> {
                val intent = Intent(requireActivity(), RunningFinishActivity::class.java)
                intent.putExtra("time", binding.tvTimeCount.text)
                intent.putExtra("distance", calculatedDistance)
                intent.putExtra("statisticId", viewModel.statistic_data.value!!.statisticId)
                startActivity(intent)
            }
            EventEnums.FAIL -> {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setMessage("Ошибка")
                alertDialogBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    dialog.dismiss()
                }
                alertDialogBuilder.show()
            }
            EventEnums.OTHER -> {
                Timber.d("ПАХАЙ 2")
            }
        }
    }

    companion object {
        /**
         * Request code for location permission request.
         *
         * @see .onRequestPermissionsResult
         */
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onPolylineClick(p0: Polyline) {
        TODO("Not yet implemented")
    }

}