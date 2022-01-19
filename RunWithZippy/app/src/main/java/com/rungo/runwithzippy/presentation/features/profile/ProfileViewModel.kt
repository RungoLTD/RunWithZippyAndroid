package com.rungo.runwithzippy.presentation.features.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.usecase.GetProfileStatisticsUseCase
import com.rungo.runwithzippy.domain.usecase.GetProfileUseCase
import com.rungo.runwithzippy.domain.usecase.ProfileUpdateUseCase
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventEnums
import timber.log.Timber

class ProfileViewModel(
    private val profileUseCase: GetProfileUseCase,
    private val profileStatisticsUseCase: GetProfileStatisticsUseCase,
    private  val profileUpdateUseCase: ProfileUpdateUseCase,
    private val sharedPreferences: PreferenceHelper
) : BaseViewModel() {

    var progressBar = MutableLiveData<Boolean>().apply { value = false }
    var profile = profileUseCase.profile_data
    var statistics = profileStatisticsUseCase.statistics
    //

    fun getProfile() {
        progressBar.value = true

        Timber.d("ACCESS TOKEN ${sharedPreferences[Constants.ACCESS_TOKEN]}")

        profileUseCase.invoke(
            viewModelScope,
            AccessTokenParam(sharedPreferences[Constants.ACCESS_TOKEN]),
            object :
                UseCaseResponse<ProfileResponse> {
                override fun onSuccess(result: ProfileResponse) {
                    println("RESULT === "+result)
                    if (result.success) {
//                        sendEvent(EventEnums.SUCCESS)
                        Timber.d("profile ${result.data}")
                        profileUseCase.profile_data.value = result.data
                        getProfileStatistics()
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

    fun getProfileStatistics() {
//        if (!profile.value.()) {
//            return
//        }

        progressBar.value = true

        Timber.d("ACCESS TOKEN ${sharedPreferences[Constants.ACCESS_TOKEN]}")

        profileStatisticsUseCase.invoke(
            viewModelScope,
            AccessTokenParam(sharedPreferences[Constants.ACCESS_TOKEN]),
            object :
                UseCaseResponse<ProfileStatisticsResponse> {
                override fun onSuccess(result: ProfileStatisticsResponse) {
                    println("RESULT === "+result)
                    if (result.success) {
                        sendEvent(EventEnums.SUCCESS)
                        Timber.d("profile ${result.data}")
                        profileStatisticsUseCase.statistics.value = result.data
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

    fun updateProfile(params: ProfileUpdateRequest) {
        progressBar.value = true
        profileUpdateUseCase.invoke(
            viewModelScope,
            params,
            object :
                UseCaseResponse<ProfileResponse> {
                override fun onSuccess(result: ProfileResponse) {
                    println("RESULT === "+result)
                    if (result.success) {
//                        sendEvent(EventEnums.SUCCESS)
                        Timber.d("profile ${result.data}")
                        profileUpdateUseCase.profile_data.value = result.data
                        getProfile()
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