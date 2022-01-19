package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ContainerFragmentFriendsBinding
import com.rungo.runwithzippy.databinding.ContainerFragmentProfileBinding
import kotlinx.android.synthetic.main.activity_profile.*

class FriendsContainer: BaseActivity() {

    private val binding: ContainerFragmentFriendsBinding by binding(R.layout.container_fragment_friends)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    override fun exit() {

    }

    private fun setupObservers() {
        connectionLiveData.observe(this, Observer {
            binding.disableNetwork.visibility = if (it) View.GONE else View.VISIBLE
        })
    }
}