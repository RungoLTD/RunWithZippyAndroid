package com.rungo.runwithzippy.presentation.features.profile

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.data.model.ProfileUpdateRequest
import com.rungo.runwithzippy.databinding.ActivityProfileBinding
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.settings_dialog.*
import org.koin.android.viewmodel.ext.android.getViewModel


class ProfileFragment : BaseFragment() {
    private val viewModel by lazy { getViewModel<ProfileViewModel>() }
    private lateinit var binding: ActivityProfileBinding
    private var username: String = ""
    private var avatar: String = ""
    private var trophyName: String = ""
    private var rebukeName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("CONTAINER")
        println(container)
        println(inflater)
        binding = binding(layoutInflater, R.layout.activity_profile, container)
        return binding.root
    }

    override fun setupObservers() {
        val progressDialog = ProgressDialog(requireContext())
        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            if (it)
                progressDialog.show()
            else
                progressDialog.hide()
        })
    }

    override fun onAfterViewCreated() {
        setupEventListener(viewModel)
        if(activity?.intent?.getStringExtra("type").toString() == "click"){
            binding.toolbar.setNavigationOnClickListener{
                this.requireActivity().onBackPressed()
            }
            binding.toolbar.setTitle("Мой профиль")
            if (this.isNetworkAvailable()) {
                viewModel.getProfile()
            } else {
                Toast.makeText(requireContext(), "Отсутствует интернет соединение", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.toolbar.visibility = View.GONE
        }
        binding.btnProfileSave.setOnClickListener {
            if (this.isNetworkAvailable()) {
                val sharedPrefences = this.requireActivity()
                    .getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
                val access_token = sharedPrefences.getString(Constants.ACCESS_TOKEN, "").toString()
                viewModel.updateProfile(
                    ProfileUpdateRequest(
                        access_token,
                        username,
                        avatar,
                        trophyName,
                        rebukeName,
                        etProfileHeight.text.toString().toInt(),
                        etProfileWeight.text.toString().toInt()
                    )
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Отсутствует интернет соединение",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> {
                println("YES")
                username = viewModel.profile.value!!.name
                avatar = viewModel.profile.value!!.avatar
                trophyName = viewModel.profile.value!!.trophy_name
                rebukeName = viewModel.profile.value!!.rebuke_name
                binding.tvProfileUsername.text = username
                binding.tvProfileUserId.text = "Ваш ID: " + viewModel.profile.value!!.id.toString()
                binding.etProfileWeight.setText(viewModel.profile.value!!.weight.toString())
                binding.etProfileHeight.setText(viewModel.profile.value!!.height.toString())
                if (avatar != "")
                    Picasso.get().load(avatar).into(binding.ivProfileAvatar)
                binding.tvMyTrainingTotalCount.text =
                    viewModel.statistics.value!!.trainCount.toString()
                binding.tvMyTrainingTempCount.text = viewModel.statistics.value!!.avgPace
                binding.tvMyTrainingSpeedCount.text = viewModel.statistics.value!!.avgSpeed
                binding.tvTrainingTotalDistanceKm.setText("%.2f".format(viewModel.statistics.value!!.distance * 0.001))
            }
            EventEnums.FAIL -> {
                Toast.makeText(requireContext(), "Не удалось получить данные", Toast.LENGTH_SHORT)
                    .show()
            }
            EventEnums.OTHER -> {
            }
        }
    }
}