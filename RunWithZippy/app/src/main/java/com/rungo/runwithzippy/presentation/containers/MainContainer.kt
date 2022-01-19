package com.rungo.runwithzippy.presentation.containers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.data.model.StepRequest
import com.rungo.runwithzippy.databinding.ActivityMainContainerBinding
import com.rungo.runwithzippy.presentation.dialogs.CallbackListener
import com.rungo.runwithzippy.presentation.features.config.InitialConfigModel
import com.rungo.runwithzippy.presentation.features.main.MainViewModel
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.KeepStateNavigator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.layout_navigation_drawer.view.*
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class MainContainer : BaseActivity(), DrawerLayout.DrawerListener, SensorEventListener, NavigationView.OnNavigationItemSelectedListener,
    CallbackListener {

    private val binding: ActivityMainContainerBinding by binding(R.layout.activity_main_container)
    private lateinit var navController: NavController
    var running = false
    var sensorManager:SensorManager? = null
    private var totalStep: Int = 0
    private var previousTotalStep: Int = 0
    private val initialConfigModel by lazy { getViewModel<InitialConfigModel>() }
    private val mainViewModel by lazy { getViewModel<MainViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
        setupNavigation()
        setupObserver()
        initMainActivity()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }


    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        println("YUUUU 222 = "+totalStep)
        if (stepsSensor == null) {
            Toast.makeText(this, "No Step Counter Sensor !", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        println("PAUSE")
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (running) {
            loadStepCount()
            totalStep = event.values[0].toInt()
            val currentSteps = totalStep - previousTotalStep
            println("STEP ==  "+event.values[0].toInt())
            println("STEP 2 ==  "+ currentSteps)
            val stepToKm = currentSteps*0.000762
            try {
                tvSteps.setText("" + currentSteps + " \nшаги")
                tvDistance.setText("%.2f \nkm".format(stepToKm))
            } catch (e: Exception){
                println(e)
            }

            changedStepCount()
        }
    }

    override fun exit() {

    }

    private fun saveStepCount(){
        val sharedPrefences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefences.edit()
        editor.putInt("countStep", totalStep)
        val sdf = SimpleDateFormat("yyyy-M-dd")
        val currentDate = sdf.format(Date())
        editor.putString("lastDateStep", currentDate)
        editor.apply()
    }

    private fun resetStep(){
        previousTotalStep = totalStep
//        saveStepCount()
    }

    private fun loadStepCount(){
        val sharedPrefences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val savedStep = sharedPrefences.getInt("countStep",0)
        //запрос в сервер, потом обнуление
        previousTotalStep = savedStep
    }

    private fun changedStepCount(){
        val sharedPrefences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val savedStep = sharedPrefences.getInt("countStep",0)
        val savedLastDateStep = sharedPrefences.getString("lastDateStep","")
        println("saved step = "+savedStep)
        println("saved step = "+savedLastDateStep)
        val sdf = SimpleDateFormat("yyyy-M-dd")
        val currentDate = sdf.format(Date())
        if(savedStep == 0 && savedLastDateStep == "") {//первый раз сохраняет
            saveStepCount()
        } else {
            if (savedLastDateStep != currentDate) {
                var lastDayStep = totalStep - savedStep
                val sharedPrefences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
                val access_token = sharedPrefences.getString(Constants.ACCESS_TOKEN,"").toString()
                mainViewModel.setStep(
                    StepRequest(
                        access_token,
                        lastDayStep,
                    )
                )
                previousTotalStep = savedStep
                saveStepCount()
            }
        }
        //запрос в сервер, потом обнуление
    }

    private fun initMainActivity(){
        if(initialConfigModel.initialConfig.value!!.user.name == ""){
            val intent = Intent(this, LoginContainer::class.java)
            startActivity(intent)
        }
        binding.tvCoin.text = initialConfigModel.initialConfig.value!!.user.coins.toString()
        binding.rlCoin.setOnClickListener {
            val intent = Intent(this, ShopContainer::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setupNavigation() {
        navController = findNavController(R.id.mainContainer)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer)!!
        val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.mainContainer)

        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.main)
        binding.navigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            println("DESTIN id = "+destination.id)
//            when (destination.id) {
//                R.id.navigation_main, R.id.navigation_training, R.id.navigation_challenge ->
//                    binding.navigation.visibility = View.VISIBLE
//                else -> binding.navigation.visibility = View.GONE
//            }
            Timber.d("CURRENT DESTINATION ID ${destination.id} ${R.id.mainFragment} ${R.id.mainContainer} ${R.id.navigation_main}")
        }
        binding.navView.setNavigationItemSelectedListener(this)
        val headerview = binding.navView.getHeaderView(0)
        headerview.tvName.text = initialConfigModel.initialConfig.value!!.user.name
        if(initialConfigModel.initialConfig.value!!.user.avatar != "")
            Picasso.get().load(initialConfigModel.initialConfig.value!!.user.avatar).into(headerview.ivMenuProfileAvatar)
        headerview.tvClickToProfile.setOnClickListener {
            val intent = Intent(this, ProfileContainer::class.java)
            intent.putExtra("type", "click")
            startActivity(intent)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_training_log -> {
                val intent = Intent(this, RunningStatisticsContainer::class.java)
                startActivity(intent)
            }
            R.id.menu_friends -> {
                val intent = Intent(this, FriendsContainer::class.java)
                startActivity(intent)
            }
            R.id.menu_skins -> {
                val intent = Intent(this, ShopContainer::class.java)
                startActivity(intent)
            }
            R.id.menu_training -> {
                val intent = Intent(this, TrainingContainer::class.java)
                startActivity(intent)
            }
            R.id.menu_achievement -> {
                val intent = Intent(this, ShopContainer::class.java)
                startActivity(intent)
            }
            R.id.menu_challenges -> {
                val intent = Intent(this, ShopContainer::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    private fun setupListeners() {
        binding.ivMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.drawerLayout.addDrawerListener(this)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = false
            binding.drawerLayout.closeDrawers()

            when (menuItem.itemId) {

            }
            true
        }
    }

    private fun setupObserver() {
        connectionLiveData.observe(this, Observer {
            binding.disableNetwork.visibility = if (it) View.GONE else View.VISIBLE
        })
    }

    override fun onDrawerStateChanged(newState: Int) {}

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        binding.fragmentContainer.x = slideOffset * drawerView.width;
        binding.drawerLayout.bringChildToFront(drawerView);
        binding.drawerLayout.requestLayout();
    }

    override fun onDrawerClosed(drawerView: View) {}

    override fun onDrawerOpened(drawerView: View) {}

    override fun onBackPressed() {
        super.onBackPressed()
        navController.popBackStack()
    }

    override fun onDataReceived(data: String) {
        TODO("Not yet implemented")
    }
}
