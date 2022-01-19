package com.rungo.runwithzippy.data.repository

import com.rungo.runwithzippy.data.model.AccessTokenParam
import com.rungo.runwithzippy.api.ApiService
import com.rungo.runwithzippy.data.model.ProfileResponse
import com.rungo.runwithzippy.data.model.ProfileStatisticsResponse
import com.rungo.runwithzippy.data.model.ProfileUpdateRequest
import com.rungo.runwithzippy.domain.repository.ProfileRepository

class ProfileRepositoryImpl(private val apiService: ApiService): ProfileRepository {

    override suspend fun getProfile(accessToken: AccessTokenParam): ProfileResponse {
        return apiService.getProfile(accessToken)
    }

    override suspend fun getProfileStatistics(accessToken: AccessTokenParam): ProfileStatisticsResponse {
        return apiService.getProfileStatistics(accessToken)
    }

    override suspend fun getProfileUpdate(profileUpdateRequest: ProfileUpdateRequest): ProfileResponse {
        return apiService.getProfileUpdate(profileUpdateRequest)
    }
}