package com.rungo.runwithzippy.presentation.features.running

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rungo.runwithzippy.R
import com.rungo.runwithzippy.base.BaseFragment
import com.rungo.runwithzippy.data.model.RunningStatistic
import com.rungo.runwithzippy.data.model.RunningStatisticsListRequest
import com.rungo.runwithzippy.databinding.FragmentRunningStatisticsListBinding
import com.rungo.runwithzippy.presentation.containers.RunningStatisticViewContainer
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventData
import com.rungo.runwithzippy.utils.EventEnums
import com.rungo.runwithzippy.utils.extensions.isNetworkAvailable
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 * A fragment representing a list of Items.
 */
class RunningStatisticsFragment : BaseFragment(), (View, RunningStatistic) -> Unit {

    private lateinit var binding: FragmentRunningStatisticsListBinding
    private val viewModel by lazy { getViewModel<RunningViewModel>() }
    private lateinit var adapter: RunningStatisticsAdapter
    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 5
    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    var page = 0
    private lateinit var mLayoutManager: LinearLayoutManager
    private var statistics_list: MutableList<RunningStatistic>? = mutableListOf()
    var access_token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(layoutInflater, R.layout.fragment_running_statistics_list, container)
        return binding.root
    }

    override fun setupObservers() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setCancelable(false)
        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            viewModel.progressBar.observe(viewLifecycleOwner, Observer {
                if (it)
                    progressDialog.show()
                else
                    progressDialog.hide()
            })
        })
    }

    override fun onAfterViewCreated() {
        setupEventListener(viewModel)

        binding.toolbar.setNavigationOnClickListener{
            this.requireActivity().onBackPressed()
        }
        binding.toolbar.setTitle("Журнал тренировок")
        if (this.isNetworkAvailable()) {
            val sharedPrefences = this.requireActivity().getSharedPreferences(
                Constants.PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
            access_token = sharedPrefences.getString(Constants.ACCESS_TOKEN, "").toString()
            viewModel.getStatisticsList(RunningStatisticsListRequest(access_token, page))
        } else {
            Toast.makeText(requireContext(), "Отсутствует интернет соединение", Toast.LENGTH_SHORT).show()
        }
        binding.btnViewStatisticsList.setOnClickListener {
            binding.rvRunningStatistics.visibility = View.VISIBLE
            binding.llViewAvgPaceSpeed.visibility = View.GONE
            binding.btnViewTotalStatistics.setBackgroundColor(getResources().getColor(R.color.colorWhite))
            binding.btnViewTotalStatistics.setTextColor(getResources().getColor(R.color.colorPrimary))
            binding.btnViewStatisticsList.setBackgroundColor(getResources().getColor(R.color.colorPrimary))
            binding.btnViewStatisticsList.setTextColor(getResources().getColor(R.color.colorWhite))
        }
        binding.btnViewTotalStatistics.setOnClickListener {
            binding.rvRunningStatistics.visibility = View.GONE
            binding.llViewAvgPaceSpeed.visibility = View.VISIBLE
            binding.btnViewTotalStatistics.setBackgroundColor(getResources().getColor(R.color.colorPrimary))
            binding.btnViewTotalStatistics.setTextColor(getResources().getColor(R.color.colorWhite))
            binding.btnViewStatisticsList.setBackgroundColor(getResources().getColor(R.color.colorWhite))
            binding.btnViewStatisticsList.setTextColor(getResources().getColor(R.color.colorPrimary))
        }
        binding.rvRunningStatistics.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                visibleItemCount = binding.rvRunningStatistics.childCount;
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)
                ) {
                    // End has been reached

                    Log.i("Yaeye!", "end called");

                    // Do something

                    loading = true;
                    page = page + 1
//                    if (Exceptions.isNetworkAvailable()) {
                        viewModel.getStatisticsList(RunningStatisticsListRequest(access_token, page))
//                    } else {
//                        Toast.makeText(requireContext(), "Отсутствует интернет соединение", Toast.LENGTH_SHORT).show()
//                    }
                }
            }
        })
    }

    override fun onEvent(eventData: EventData) {
        when (eventData.eventCode) {
            EventEnums.SUCCESS -> {
                if (viewModel.total_profile_statistics.value != null) {
                    println("Distance")
                    println(viewModel.total_profile_statistics.value!!.distance)
                    if (viewModel.total_profile_statistics.value!!.distance != null)
                        binding.tvTotalDistance.setText("%.2f".format(viewModel.total_profile_statistics.value!!.distance * 0.001))
                    binding.tvTotalTrains.text =
                        "" + viewModel.total_profile_statistics.value!!.trainCount
                }
                println("statistics_list === ")
                println(viewModel.statistics_list.value)
                if (viewModel.statistics_list.value != null) {
                    println("YES")
                    mLayoutManager = LinearLayoutManager(requireContext())
                    statistics_list?.addAll(viewModel.statistics_list.value!!)
//                    statistics_list.
                    adapter = RunningStatisticsAdapter(statistics_list, this)
//                    statistics_list.addAll(viewModel.statistics_list.value!!)
                    binding.rvRunningStatistics.adapter = adapter
                    binding.rvRunningStatistics.layoutManager = mLayoutManager
                    adapter.notifyDataSetChanged()
                } else {
                    println("NO")
//                    binding.rvRunningStatistics.visibility = View.GONE
                }
            }
        }
    }

    override fun invoke(p1: View, p2: RunningStatistic) {
        val intent = Intent(requireActivity(), RunningStatisticViewContainer::class.java)
        intent.putExtra("statisticId", p2.id)
        startActivity(intent)
    }
}