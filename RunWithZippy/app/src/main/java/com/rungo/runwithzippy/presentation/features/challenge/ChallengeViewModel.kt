package com.rungo.runwithzippy.presentation.features.challenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.usecase.GetChallengesUseCase
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventEnums
import timber.log.Timber

class ChallengeViewModel constructor(
    private val challengesUseCase: GetChallengesUseCase,
    private val sharedPreferences: PreferenceHelper
) : BaseViewModel() {

    var progressBar = MutableLiveData<Boolean>().apply { value = false }
    var challenges = MutableLiveData<List<Challenge>>().apply { value = null }

    fun getChallenges() {
        progressBar.value = true

        Timber.d("ACCESS TOKEN ${sharedPreferences[Constants.ACCESS_TOKEN]}")

        challengesUseCase.invoke(viewModelScope, AccessTokenParam(sharedPreferences[Constants.ACCESS_TOKEN]), object : UseCaseResponse<ChallengeResponse> {
            override fun onSuccess(result: ChallengeResponse) {
                if (result.success) {
                    challenges.value = result.data
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