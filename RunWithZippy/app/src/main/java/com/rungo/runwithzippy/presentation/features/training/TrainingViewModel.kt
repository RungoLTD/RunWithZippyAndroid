package com.rungo.runwithzippy.presentation.features.training

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.usecase.GetTrainingUseCase
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventEnums
import timber.log.Timber

class TrainingViewModel(
    private val trainingUseCase: GetTrainingUseCase,
    private val sharedPreferences: PreferenceHelper
) : BaseViewModel() {

    var progressBar = MutableLiveData<Boolean>().apply { value = false }
    var trainings = MutableLiveData<List<List<Training>>>().apply { value = null }

    init {
        getTrainings()
    }

    fun getTrainings() {
        progressBar.value = true

        Timber.d("ACCESS TOKEN ${sharedPreferences[Constants.ACCESS_TOKEN]}")

        trainingUseCase.invoke(viewModelScope, AccessTokenParam(sharedPreferences[Constants.ACCESS_TOKEN]), object :
            UseCaseResponse<TrainingResponse> {
            override fun onSuccess(result: TrainingResponse) {
                if (result.success) {
                    Timber.d("TRAINING ${result.data}")
                    trainings.value = result.data
                } else {
                    sendEvent(EventEnums.FAIL)
                }

                progressBar.value = false
            }

            override fun onError(errorModel: ErrorModel?) {
                sendEvent(EventEnums.FAIL, errorModel?.message)
                progressBar.value = false
            }
        })
    }
}