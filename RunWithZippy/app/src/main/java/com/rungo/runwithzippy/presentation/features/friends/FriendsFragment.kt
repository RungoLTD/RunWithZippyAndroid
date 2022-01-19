package com.rungo.runwithzippy.presentation.features.friends

import android.app.ProgressDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.internal.LinkedTreeMap
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.data.model.Friend
import com.rungo.runwithzippy.data.model.RunningStatistic
import com.rungo.runwithzippy.databinding.FriendsFragmentBinding
import com.rungo.runwithzippy.databinding.ShopFragmentBinding
import com.rungo.runwithzippy.presentation.features.config.InitialConfigModel
import com.rungo.runwithzippy.presentation.features.running.RunningStatisticsAdapter
import com.rungo.runwithzippy.presentation.features.shop.ShopViewModel
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import org.koin.android.viewmodel.ext.android.getViewModel

class FriendsFragment : BaseFragment(), (View, Friend) -> Unit {

    private val viewModel by lazy { getViewModel<FriendsViewModel>() }
    private val initialConfigModel by lazy { getViewModel<InitialConfigModel>() }
    private lateinit var binding: FriendsFragmentBinding
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(layoutInflater, R.layout.friends_fragment, container)
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
        binding.toolbar.setNavigationOnClickListener{
            this.requireActivity().onBackPressed()
        }
        binding.toolbar.setTitle("Друзья")
        binding.tvProfileUsername.text = initialConfigModel.initialConfig.value!!.user.name
        binding.tvProfileUserId.text = "Ваш ID: " + initialConfigModel.initialConfig.value!!.user.id
        initFriendsList()
    }

    private fun initFriendsList(){
        if (this.isNetworkAvailable()) {
            viewModel.getFriendsList()
        } else {
            Toast.makeText(requireContext(), "Отсутствует интернет соединение", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> {
                if(viewModel.friends_list.value!!.approved.size > 0){
                    mLayoutManager = LinearLayoutManager(requireContext())
                    val adapter =
                        FriendsListAdapter(viewModel.friends_list.value!!.approved, this)
                    binding.rvFriendList.adapter = adapter
                    binding.rvFriendList.layoutManager = mLayoutManager
                    adapter.notifyDataSetChanged()
                } else {
                    binding.rvFriendList.visibility = View.GONE
                    binding.tvNotFriends.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun invoke(p1: View, p2: Friend) {
        println(p2.name)
    }

}