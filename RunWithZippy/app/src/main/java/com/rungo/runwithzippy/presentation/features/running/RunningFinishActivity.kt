package com.rungo.runwithzippy.presentation.features.running

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.data.model.RunningUpdateRequest
import com.rungo.runwithzippy.presentation.containers.MainContainer
import com.rungo.runwithzippy.presentation.features.profile.ProfileViewModel
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import kotlinx.android.synthetic.main.activity_running_finish.*
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber

class RunningFinishActivity : BaseActivity() {
    private val runningViewModel by lazy { getViewModel<RunningViewModel>() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running_finish)
        val time: String = intent.getStringExtra("time").toString()
        val distance: Long = intent.getLongExtra("distance", 0)
        val statisticId: Int = intent.getIntExtra("statisticId", 0)
        println("TIME == " + time)
        println("DISTANCE == " + distance)
        println("statisticId == " + statisticId)
        tvFinishDistanceKm.text = "%.2f".format(distance * 0.001)
        tvFinishTimeCount.text = time
        setupEventListener(this, runningViewModel)
        btnRunningFinishSave.setOnClickListener({
            if (this.isNetworkAvailable()) {
                val sharedPrefences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
                val access_token = sharedPrefences.getString(Constants.ACCESS_TOKEN,"").toString()
                runningViewModel.statisticsUpdate(
                    RunningUpdateRequest(
                        access_token,
                        statisticId,
                        seekBar.progress,
                        etTrainingName.text.toString(),
                        false
                    )
                )
            } else {
                Toast.makeText(this, "Отсутствует интернет соединение", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> {
                val intent = Intent(this, MainContainer::class.java)
                startActivity(intent)
            }
            EventEnums.FAIL -> {
                val alertDialogBuilder = AlertDialog.Builder(this)
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

    override fun exit() {
        TODO("Not yet implemented")
    }
}