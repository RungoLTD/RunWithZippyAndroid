package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.*

interface FriendsRepository {
    suspend fun getFriendsList(accessToken: AccessTokenParam): FriendsResponse

//    suspend fun getProfileStatistics(accessToken: AccessTokenParam): ProfileStatisticsResponse
//
//    suspend fun getProfileUpdate(profileUpdateRequest: ProfileUpdateRequest): ProfileResponse
}