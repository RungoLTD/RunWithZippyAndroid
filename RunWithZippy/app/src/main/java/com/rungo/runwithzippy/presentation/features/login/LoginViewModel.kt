package com.rungo.runwithzippy.presentation.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.usecase.ProfileUpdateUseCase
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithEmailUseCase
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithFacebookUseCase
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithGoogleUseCase
import com.rungo.runwithzippy.presentation.features.config.InitialConfigModel
import com.rungo.runwithzippy.utils.Event
import com.rungo.runwithzippy.utils.EventEnums
import org.koin.android.viewmodel.ext.android.getViewModel
import timber.log.Timber

class LoginViewModel constructor(
    private val authWithEmailUseCase: AuthWithEmailUseCase,
    private val authWithGoogleUseCase: AuthWithGoogleUseCase,
    private val authWithFacebookUseCase: AuthWithFacebookUseCase,
    private  val profileUpdateUseCase: ProfileUpdateUseCase
    ): BaseViewModel() {

    var progressBar = MutableLiveData<Boolean>().apply { value = false }

    fun authWithEmail(params: AuthWithEmail) {
        progressBar.postValue(true)
        println("SEND ====")
        authWithEmailUseCase.invoke(viewModelScope, params, object : UseCaseResponse<AuthResponse> {
            override fun onSuccess(result: AuthResponse) {
                println("RESULT ====")
                println(result)
                if (result.success) {
                    println("YES")
                    progressBar.value = false
                    authWithEmailUseCase.setAccessToken(result)
//                    result.data.user_data.name
                    sendEvent(EventEnums.SUCCESS_LOGIN)
                } else {
                    Timber.d("SUCCESS ERROR")
                    progressBar.postValue(false)
                    sendEvent(EventEnums.FAIL, result.error)
                }
            }

            override fun onError(errorModel: ErrorModel?) {
                Timber.d("SUCCESS ERROR ERROR")
                progressBar.value = false
                println("ERROR ==")
                println(errorModel?.message)
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
                        Timber.d("profile ${result.data}")
                        sendEvent(EventEnums.SUCCESS_LOGIN)
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