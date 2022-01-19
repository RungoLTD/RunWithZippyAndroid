package com.rungo.runwithzippy.presentation.containers

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseActivity
import com.rungo.runwithzippy.databinding.ContainerFragmentProfileBinding
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileContainer: BaseActivity() {

    private val binding: ContainerFragmentProfileBinding by binding(R.layout.container_fragment_profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val time: String = intent.getStringExtra("time").toString()
        val distance: Long = intent.getLongExtra("distance", 0)
        val statisticId: Int = intent.getIntExtra("statisticId", 0)
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