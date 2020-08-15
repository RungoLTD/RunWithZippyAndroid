package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ActivityLoginContainerBinding

class LoginContainer : BaseActivity() {

    private val binding: ActivityLoginContainerBinding by binding(R.layout.activity_login_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        setupObservers()
    }

    private fun setupObservers() {
        connectionLiveData.observe(this, Observer {
            binding.disableNetwork.visibility = if (it) View.GONE else View.VISIBLE
        })
    }
}