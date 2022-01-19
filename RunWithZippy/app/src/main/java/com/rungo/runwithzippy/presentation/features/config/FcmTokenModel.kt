package com.rungo.runwithzippy.presentation.features.config

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.rungo.runwithzippy.BuildConfig
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.usecase.GetInitialConfigUseCase
import com.rungo.runwithzippy.domain.usecase.SendFcmTokenUseCase
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventEnums
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber

class FcmTokenModel (
    private val sendFcmTokenUseCase: SendFcmTokenUseCase,
    private val sharedPreferences: PreferenceHelper
) : BaseViewModel() {

    var progressBar = MutableLiveData<Boolean>().apply { value = false }

    fun setFcmToken(token: String, initialConfigModel: InitialConfigModel) {
        progressBar.value = true

        Timber.d("ACCESS TOKEN ${sharedPreferences[Constants.ACCESS_TOKEN]}")

        sendFcmTokenUseCase.invoke(
            viewModelScope,
            SendFcmToken(
                sharedPreferences[Constants.ACCESS_TOKEN], 0, BuildConfig.VERSION_CODE,
                BuildConfig.VERSION_NAME, token
            ),
            object :
                UseCaseResponse<FcmTokenResponse> {
                override fun onSuccess(result: FcmTokenResponse) {
                    println("RESULT === " + result)
                    if (result.success) {

                        initialConfigModel.getInitialConfig()
                        sendEvent(EventEnums.SUCCESS)
                    } else {
                        sendEvent(EventEnums.FAIL)
                    }

                    progressBar.value = false
                }

                override fun onError(errorModel: ErrorModel?) {
                    println("RESULT FAIL === " + errorModel?.message)
                    sendEvent(EventEnums.FAIL, errorModel?.message)
                    progressBar.value = false
                }
            })
    }
}