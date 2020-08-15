package com.rungo.runwithzippy.presentation.features.splash

import android.content.Intent
import android.os.Bundle
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.presentation.containers.LoginContainer
import com.rungo.runwithzippy.presentation.containers.MainContainer
import org.koin.android.viewmodel.ext.android.getViewModel

class SplashActivity : BaseActivity() {

    private val viewModel by lazy { getViewModel<SplashViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openNextScreen()
    }

    private fun openNextScreen() {
        if (viewModel.isAccessTokenAvailable()) {
            val intent = Intent(this, MainContainer::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginContainer::class.java)
            startActivity(intent)
        }
        finish()
    }
}