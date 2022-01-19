package com.rungo.runwithzippy.presentation.features.friends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.base.UseCaseResponse
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.usecase.friends.GetFriendsUseCase
import com.rungo.runwithzippy.utils.Constants
import com.rungo.runwithzippy.utils.EventEnums
import timber.log.Timber

class FriendsViewModel constructor(
    private val friendsUseCase: GetFriendsUseCase,
    private val sharedPreferences: PreferenceHelper
) : BaseViewModel() {
    var progressBar = MutableLiveData<Boolean>().apply { value = false }
    var friends_list = friendsUseCase.friends_list

    fun getFriendsList() {
        progressBar.value = true

        Timber.d("ACCESS TOKEN ${sharedPreferences[Constants.ACCESS_TOKEN]}")

        friendsUseCase.invoke(
            viewModelScope,
            AccessTokenParam(sharedPreferences[Constants.ACCESS_TOKEN]),
            object :
                UseCaseResponse<FriendsResponse> {
                override fun onSuccess(result: FriendsResponse) {
                    println("RESULT === "+result)
                    if (result.success) {
                        Timber.d("skins ${result.data}")
                        friendsUseCase.friends_list.value = result.data
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
}