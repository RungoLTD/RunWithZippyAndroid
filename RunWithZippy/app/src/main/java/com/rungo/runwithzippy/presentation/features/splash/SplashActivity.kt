package com.rungo.runwithzippy.presentation.features.splash

import android.content.Intent
import android.os.Bundle
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.presentation.containers.LoginContainer

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openNextScreen()

    }

    private fun openNextScreen() {
        val intent = Intent(this, LoginContainer::class.java)
        startActivity(intent)
        finish()
    }
}