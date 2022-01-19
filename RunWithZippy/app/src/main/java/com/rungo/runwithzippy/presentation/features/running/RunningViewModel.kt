package com.rungo.runwithzippy.presentation.features.running

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.usecase.GetProfileStatisticsUseCase
import com.rungo.runwithzippy.domain.usecase.running_statistic.StatisticGetViewUseCase
import com.rungo.runwithzippy.domain.usecase.running_statistic.StatisticsAddUseCase
import com.rungo.runwithzippy.domain.usecase.running_statistic.StatisticsGetUseCase
import com.rungo.runwithzippy.domain.usecase.running_statistic.StatisticsUpdateUseCase
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventEnums
import timber.log.Timber

class RunningViewModel constructor(
    private val statisticsAddUseCase: StatisticsAddUseCase,
    private val statisticsUpdateUseCase: StatisticsUpdateUseCase,
    private val profileStatisticsUseCase: GetProfileStatisticsUseCase,
    private val statisticsGetUseCase: StatisticsGetUseCase,
    private val statisticViewUseCase: StatisticGetViewUseCase,
    private val sharedPreferences: PreferenceHelper
) : BaseViewModel() {
    var progressBar = MutableLiveData<Boolean>().apply { value = false }
    var statistic_data = statisticsAddUseCase.statistic_data
    var statistics_list = statisticsGetUseCase.statistics_list
    var total_profile_statistics = profileStatisticsUseCase.statistics
    var get_statistic = statisticViewUseCase.statistic

    fun statisticsAdd(params: RunningRequest) {
        progressBar.postValue(true)
        println("SEND ==== "+params)
        statisticsAddUseCase.invoke(viewModelScope, params, object : UseCaseResponse<RunningResponse> {
            override fun onSuccess(result: RunningResponse) {
                println("RESULT ====")
                println(result)
                if (result.success) {
                    progressBar.value = false
//                    regWithEmailUseCase.setAccessToken(result)
                    statisticsAddUseCase.statistic_data.value = result.data
                    sendEvent(EventEnums.SUCCESS)
                } else {
                    Timber.d("SUCCESS ERROR")
                    progressBar.postValue(false)
                    sendEvent(EventEnums.FAIL)
                }
            }

            override fun onError(errorModel: ErrorModel?) {
                Timber.d("SUCCESS ERROR ERROR")
                progressBar.value  = false
                println("ERROR ==")
                println(errorModel?.message)
                sendEvent(EventEnums.FAIL, errorModel?.message)
            }
        })
    }

    fun statisticsUpdate(params: RunningUpdateRequest) {
        progressBar.postValue(true)
        println("SEND ==== "+params)
        statisticsUpdateUseCase.invoke(viewModelScope, params, object : UseCaseResponse<RunningResponse> {
            override fun onSuccess(result: RunningResponse) {
                println("RESULT ====")
                println(result)
                if (result.success) {
                    progressBar.value = false
//                    regWithEmailUseCase.setAccessToken(result)
                    statisticsUpdateUseCase.statistic_data.value = result.data
                    sendEvent(EventEnums.SUCCESS)
                } else {
                    Timber.d("SUCCESS ERROR")
                    progressBar.postValue(false)
                    sendEvent(EventEnums.FAIL)
                }
            }

            override fun onError(errorModel: ErrorModel?) {
                Timber.d("SUCCESS ERROR ERROR")
                progressBar.value  = false
                println("ERROR ==")
                println(errorModel?.message)
                sendEvent(EventEnums.FAIL, errorModel?.message)
            }
        })
    }

    fun getProfileStatistics() {
        progressBar.postValue(true)

        Timber.d("ACCESS TOKEN ${sharedPreferences[Constants.ACCESS_TOKEN]}")

        profileStatisticsUseCase.invoke(
            viewModelScope,
            AccessTokenParam(sharedPreferences[Constants.ACCESS_TOKEN]),
            object :
                UseCaseResponse<ProfileStatisticsResponse> {
                override fun onSuccess(result: ProfileStatisticsResponse) {
                    println("RESULT ProfileStatisticsResponse === "+result)
                    if (result.success) {
                        Timber.d("profile ${result.data}")
                        profileStatisticsUseCase.statistics.value = result.data
                        sendEvent(EventEnums.SUCCESS)
                    } else {
                        sendEvent(EventEnums.FAIL)
                    }

                    progressBar.value = false
                }

                override fun onError(errorModel: ErrorModel?) {
                    println("RESULT FAIL === "+errorModel?.message)
                    sendEvent(EventEnums.FAIL, errorModel?.message)
                    progressBar.value = false
                }
            })
    }

    fun getStatisticsList(params: RunningStatisticsListRequest){
        progressBar.postValue(true)
        println("SEND ==== "+params)
        statisticsGetUseCase.invoke(viewModelScope, params, object : UseCaseResponse<GetRunningStatisticsResponse> {
            override fun onSuccess(result: GetRunningStatisticsResponse) {
                println("RESULT ====")
                println(result)
                if (result.success) {
                    progressBar.value = false
//                    regWithEmailUseCase.setAccessToken(result)
                    statisticsGetUseCase.statistics_list.value = result.data
                    getProfileStatistics()
                } else {
                    Timber.d("SUCCESS ERROR")
                    progressBar.postValue(false)
                    sendEvent(EventEnums.FAIL)
                }
            }

            override fun onError(errorModel: ErrorModel?) {
                Timber.d("SUCCESS ERROR ERROR")
                progressBar.value  = false
                println("ERROR ==")
                println(errorModel?.message)
                sendEvent(EventEnums.FAIL, errorModel?.message)
            }
        })
    }

    fun getStatisticView(params: RunningStatisticsViewRequest){
        progressBar.postValue(true)
        println("SEND ==== "+params)
        statisticViewUseCase.invoke(viewModelScope, params, object : UseCaseResponse<GetRunningStatisticViewResponse> {
            override fun onSuccess(result: GetRunningStatisticViewResponse) {
                println("RESULT ====")
                println(result)
                if (result.success) {
                    progressBar.value = false
                    statisticViewUseCase.statistic.value = result.data
                    sendEvent(EventEnums.SUCCESS)
                } else {
                    Timber.d("SUCCESS ERROR")
                    progressBar.postValue(false)
                    sendEvent(EventEnums.FAIL)
                }
            }

            override fun onError(errorModel: ErrorModel?) {
                Timber.d("SUCCESS ERROR ERROR")
                progressBar.value  = false
                println("ERROR ==")
                println(errorModel?.message)
                sendEvent(EventEnums.FAIL, errorModel?.message)
            }
        })
    }
}