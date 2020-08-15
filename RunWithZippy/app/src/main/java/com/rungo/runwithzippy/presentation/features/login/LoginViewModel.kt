package com.rungo.runwithzippy.presentation.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.AuthResponse
import com.rungo.runwithzippy.data.model.AuthWithEmail
import com.rungo.runwithzippy.data.model.AuthWithGoogle
import com.rungo.runwithzippy.data.model.ErrorModel
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithEmailUseCase
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithFacebookUseCase
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithGoogleUseCase
import com.rungo.runwithzippy.utils.Event
import com.rungo.runwithzippy.utils.EventEnums
import timber.log.Timber

class LoginViewModel constructor(
    private val authWithEmailUseCase: AuthWithEmailUseCase,
    private val authWithGoogleUseCase: AuthWithGoogleUseCase,
    private val authWithFacebookUseCase: AuthWithFacebookUseCase
): BaseViewModel() {

    var progressBar = MutableLiveData<Boolean>().apply { value = false }

    fun authWithEmail(params: AuthWithEmail) {
        progressBar.postValue(true)
        authWithEmailUseCase.invoke(viewModelScope, params, object : UseCaseResponse<AuthResponse> {
            override fun onSuccess(result: AuthResponse) {
                if (result.success) {
                    progressBar.value = false
                    authWithEmailUseCase.setAccessToken(result)
                    sendEvent(EventEnums.SUCCESS)
                } else {
                    Timber.d("SUCCESS ERROR")
                    progressBar.postValue(false)
                    sendEvent(EventEnums.FAIL, result.error)
                }
            }

            override fun onError(errorModel: ErrorModel?) {
                Timber.d("SUCCESS ERROR ERROR")
                progressBar.value  = false
                sendEvent(EventEnums.FAIL, errorModel?.message)
            }
        })
    }

    fun authWithGoogle(params: AuthWithGoogle) {
        progressBar.value = true
        authWithGoogleUseCase.invoke(viewModelScope, params, object: UseCaseResponse<AuthResponse> {
            override fun onSuccess(result: AuthResponse) {
                if (result.success) {
                    progressBar.value = false
                    authWithEmailUseCase.setAccessToken(result)
                    sendEvent(EventEnums.SUCCESS)
                } else {
                    Timber.d("SUCCESS ERROR")
                    progressBar.value = false
                    sendEvent(EventEnums.FAIL, result.error)
                }
            }

            override fun onError(errorModel: ErrorModel?) {
                Timber.d("SUCCESS ERROR ERROR")
                progressBar.value = false
                sendEvent(EventEnums.FAIL, errorModel?.message)
            }
        })
    }


}