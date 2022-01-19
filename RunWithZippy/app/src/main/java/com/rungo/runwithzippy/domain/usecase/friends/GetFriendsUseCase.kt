package com.rungo.runwithzippy.domain.usecase.friends

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.ChallengeRepository
import com.rungo.runwithzippy.domain.repository.FriendsRepository
import com.rungo.runwithzippy.domain.repository.ProfileRepository
import com.rungo.runwithzippy.utils.Constants

class GetFriendsUseCase constructor(
    private val friendsRepository: FriendsRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<FriendsResponse, Any?>(apiErrorHandle) {

    var friends_list = MutableLiveData<dataFriends>().apply { value = null }

    override suspend fun run(params: Any?): FriendsResponse {
        return friendsRepository.getFriendsList(params as AccessTokenParam)
    }
}