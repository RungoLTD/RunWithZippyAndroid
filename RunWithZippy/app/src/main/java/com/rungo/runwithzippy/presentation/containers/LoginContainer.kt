package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ActivityLoginContainerBinding

class LoginContainer : BaseActivity() {

    private val binding: ActivityLoginContainerBinding by binding(R.layout.activity_login_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        setupListeners()
    }

    private fun setupListeners() {
//        binding.tvSignInEmail.setOnClickListener {
//            Toast.makeText(this, "Email", Toast.LENGTH_LONG).show()
//        }
    }
}