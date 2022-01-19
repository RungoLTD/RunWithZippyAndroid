package com.rungo.runwithzippy.presentation.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.usecase.running_statistic.StatisticsStepUseCase
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventEnums
import timber.log.Timber

class MainViewModel(
    private val statisticsStepUseCase: StatisticsStepUseCase,
    private val sharedPreferences: PreferenceHelper
) : BaseViewModel() {

    var progressBar = MutableLiveData<Boolean>().apply { value = false }
    var profile = statisticsStepUseCase.statistic_data
    //

    fun setStep(params: StepRequest) {
//        if (!profile.value.()) {
//            return
//        }

        progressBar.value = true

        Timber.d("ACCESS TOKEN ${sharedPreferences[Constants.ACCESS_TOKEN]}")
        println("Request params = "+params)
        statisticsStepUseCase.invoke(
            viewModelScope,
            params,
            object :
                UseCaseResponse<RunningResponse> {
                override fun onSuccess(result: RunningResponse) {
                    println("RESULT === "+result)
                    if (result.success) {
                        sendEvent(EventEnums.SUCCESS)
                        Timber.d("profile ${result}")
//                        profileUseCase.profile_data.value = result.data
//                        getProfileStatistics()
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
}