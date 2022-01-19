package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.*

interface FcmTokenRepository {
    suspend fun sendFcmToken(accessToken: SendFcmToken): FcmTokenResponse
}