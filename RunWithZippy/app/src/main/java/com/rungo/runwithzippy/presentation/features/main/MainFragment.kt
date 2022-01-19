package com.rungo.runwithzippy.presentation.features.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.data.services.FcmToken
import com.rungo.runwithzippy.databinding.FragmentMainBinding
import com.rungo.runwithzippy.presentation.containers.RunningContainer
import com.rungo.runwithzippy.presentation.dialogs.CallbackListener
import com.rungo.runwithzippy.presentation.dialogs.SettingsDialog
import com.rungo.runwithzippy.presentation.features.config.InitialConfigModel
import com.rungo.runwithzippy.utils.AnimationEnum
import com.rungo.runwithzippy.utils.animationModel.Zippy
import com.rungo.runwithzippy.utils.extensions.getScreenWidth
import org.koin.android.viewmodel.ext.android.getViewModel


class MainFragment : AndroidFragmentApplication(), CallbackListener {

    private lateinit var binding: FragmentMainBinding

    private val cfg: AndroidApplicationConfiguration by lazy { AndroidApplicationConfiguration() }
    private val initialConfigModel by lazy { getViewModel<InitialConfigModel>() }
    private var zippyView: View? = null
    private var zippy: Zippy? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setupZippy()
        setupListeners()
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListeners() {
        binding.btnStart.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED) {
                if (!isLocationEnabled(requireContext()))
                    showLocationIsDisabledAlert()
                else
                    openRunningScreen()
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 200)
            }
        }

        binding.tvSteps.setOnClickListener {
            binding.tvSteps.visibility = View.GONE
            binding.tvDistance.visibility = View.VISIBLE
        }

        binding.tvDistance.setOnClickListener {
            binding.tvSteps.visibility = View.VISIBLE
            binding.tvDistance.visibility = View.GONE
        }

        binding.progressBar.progress = initialConfigModel.initialConfig.value!!.user.mood
        binding.btnImgMusicApps.setOnClickListener {
            val intent = requireContext().packageManager.getLaunchIntentForPackage("com.spotify.music")
            startActivity(intent)
        }
        binding.btnImgSettings.setOnClickListener {
            showSettingAlert()
        }

        zippyView?.setOnTouchListener { view, motionEvent ->
            val action = motionEvent.action

            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                zippy?.setAnimate(AnimationEnum.HI.animationName)
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

        var animationZippy = ""
        println("MOOD")
        println(initialConfigModel.initialConfig.value!!.user.mood)
        when (initialConfigModel.initialConfig.value!!.user.mood) {
            in 0..20 -> animationZippy = AnimationEnum.BREATHES.animationName
            in 20..40 -> animationZippy = AnimationEnum.SPASM.animationName
            in 40..60 -> animationZippy = AnimationEnum.CRY.animationName
            in 60..80 -> animationZippy = AnimationEnum.EXHALE.animationName
            in 80..100 -> animationZippy = AnimationEnum.STOMACH.animationName
            else -> animationZippy = AnimationEnum.HI.animationName
        }
        zippy = Zippy(requireContext(), 0.5f, getScreenWidth(requireContext()) / 24.0f, 0.0f, animationZippy)

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
            ViewGroup.LayoutParams(
                getScreenWidth(requireContext()),
                getScreenWidth(requireContext())
            )
        )
    }

    private fun showSettingAlert(){
        val dialogFragment = SettingsDialog(this, requireContext())
        getFragmentManager()?.let { dialogFragment.show(it, "signature") }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            200 -> {
                if (grantResults.isNotEmpty() && grantResults.all { gr -> gr == PackageManager.PERMISSION_GRANTED }) {
                    if (!isLocationEnabled(requireContext()))
                        showLocationIsDisabledAlert()
                    else
                        openRunningScreen()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.turn_on_geolocation),
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }


    private fun isLocationEnabled(mContext: Context): Boolean {
        val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER
//        )
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
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

    private fun openRunningScreen() {
        val intent = Intent(requireActivity(), RunningContainer::class.java)
        startActivity(intent)
    }

    override fun onDataReceived(data: String) {
        TODO("Not yet implemented")
    }
}