package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ContainerFragmentProfileBinding
import com.rungo.runwithzippy.databinding.ContainerFragmentShopBinding
import kotlinx.android.synthetic.main.activity_profile.*

class ShopContainer: BaseActivity() {

    private val binding: ContainerFragmentShopBinding by binding(R.layout.container_fragment_shop)

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

//    private lateinit var binding: ContainerFragmentProfileBinding
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        binding = ContainerFragmentProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }

}