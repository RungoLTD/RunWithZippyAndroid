package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.*

interface ProfileRepository {
    suspend fun getProfile(accessToken: AccessTokenParam): ProfileResponse

    suspend fun getProfileStatistics(accessToken: AccessTokenParam): ProfileStatisticsResponse

    suspend fun getProfileUpdate(profileUpdateRequest: ProfileUpdateRequest): ProfileResponse
}