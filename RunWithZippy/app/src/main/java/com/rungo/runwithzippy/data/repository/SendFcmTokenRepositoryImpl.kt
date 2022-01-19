package com.rungo.runwithzippy.data.repository

import com.rungo.runwithzippy.api.ApiService
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.repository.FcmTokenRepository
import com.rungo.runwithzippy.domain.repository.InitialConfigRepository

class SendFcmTokenRepositoryImpl(private val apiService: ApiService): FcmTokenRepository {

    override suspend fun sendFcmToken(accessToken: SendFcmToken): FcmTokenResponse {
        return apiService.sendFcmToken(accessToken)
    }
}