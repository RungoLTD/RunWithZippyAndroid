package com.rungo.runwithzippy.presentation.features.running

import android.os.CountDownTimer
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.rungo.runwithzippy.base.BaseViewModel

class RunningViewModel : BaseViewModel() {

    fun setupTimer(textView: TextView) {
        val timer = object : CountDownTimer(4000, 1000) {
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                textView.text = (millisUntilFinished / 1000).toString()
            }
        }

        timer.start()
    }
}