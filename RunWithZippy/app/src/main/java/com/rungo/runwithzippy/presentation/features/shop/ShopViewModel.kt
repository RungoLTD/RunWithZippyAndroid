package com.rungo.runwithzippy.presentation.features.shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.usecase.StoreApplySkinUseCase
import com.rungo.runwithzippy.domain.usecase.StoreBuySkinUseCase
import com.rungo.runwithzippy.domain.usecase.StoreGetSkinsUseCase
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventEnums
import timber.log.Timber

class ShopViewModel(
    private val getSkinsUseCase: StoreGetSkinsUseCase,
    private val buySkinsUseCase: StoreBuySkinUseCase,
    private val applySkinsUseCase: StoreApplySkinUseCase,
    private val sharedPreferences: PreferenceHelper
) : BaseViewModel() {

    var progressBar = MutableLiveData<Boolean>().apply { value = false }
    var skins_data = getSkinsUseCase.skins_data

    fun getSkins() {
        progressBar.value = true

        Timber.d("ACCESS TOKEN ${sharedPreferences[Constants.ACCESS_TOKEN]}")

        getSkinsUseCase.invoke(
            viewModelScope,
            GetSkinsRequest(sharedPreferences[Constants.ACCESS_TOKEN], "1"),
            object :
                UseCaseResponse<GetSkinsResponse> {
                override fun onSuccess(result: GetSkinsResponse) {
                    println("RESULT === "+result)
                    if (result.success) {
                        sendEvent(EventEnums.SUCCESS)
                        Timber.d("skins ${result.data}")
                        getSkinsUseCase.skins_data.value = result.data
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

    fun buySkin(params: SkinRequest) {
        progressBar.value = true
        println("REQUEST = "+params)
        buySkinsUseCase.invoke(
            viewModelScope,
            params,
            object :
                UseCaseResponse<SkinResponse> {
                override fun onSuccess(result: SkinResponse) {
                    println("RESULT === "+result)
                    if (result.success != "false") {
//                        sendEvent(EventEnums.SUCCESS)
                        Timber.d("profile ${result.success}")
                        getSkins()

                    } else {
                        sendEvent(EventEnums.FAIL, result.error)
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

    fun applySkin(params: SkinRequest) {
        progressBar.value = true
        println("REQUEST = "+params)
        applySkinsUseCase.invoke(
            viewModelScope,
            params,
            object :
                UseCaseResponse<SkinResponse> {
                override fun onSuccess(result: SkinResponse) {
                    println("RESULT === "+result)
                    if (result.success == "true") {
//                        sendEvent(EventEnums.SUCCESS)
                        Timber.d("profile ${result.success}")
                        getSkins()
                    } else {
                        sendEvent(EventEnums.FAIL, result.error)
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