package com.rungo.runwithzippy.data.repository

import com.rungo.runwithzippy.api.ApiService
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.repository.FriendsRepository
import com.rungo.runwithzippy.domain.repository.ProfileRepository

class FriendsRepositoryImpl(private val apiService: ApiService): FriendsRepository {

    override suspend fun getFriendsList(accessToken: AccessTokenParam): FriendsResponse {
        return apiService.friendsList(accessToken)
    }

//    override suspend fun getProfileStatistics(accessToken: AccessTokenParam): ProfileStatisticsResponse {
//        return apiService.getProfileStatistics(accessToken)
//    }
//
//    override suspend fun getProfileUpdate(profileUpdateRequest: ProfileUpdateRequest): ProfileResponse {
//        return apiService.getProfileUpdate(profileUpdateRequest)
//    }
}