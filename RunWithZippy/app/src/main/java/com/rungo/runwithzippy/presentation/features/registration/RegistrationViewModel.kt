package com.rungo.runwithzippy.presentation.features.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.usecase.registration.RegistrationWithEmailUseCase
import com.rungo.runwithzippy.utils.EventEnums
import timber.log.Timber

class RegistrationViewModel constructor(private val regWithEmailUseCase: RegistrationWithEmailUseCase) : BaseViewModel() {

    var progressBar = MutableLiveData<Boolean>().apply { value = false }

    fun regWithEmail(params: RegWithEmail) {
        progressBar.postValue(true)
        println("SEND ====")
        regWithEmailUseCase.invoke(viewModelScope, params, object : UseCaseResponse<RegResponse> {
            override fun onSuccess(result: RegResponse) {
                println("RESULT ====")
                println(result)
                if (result.success) {
                    progressBar.value = false
//                    regWithEmailUseCase.setAccessToken(result)
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