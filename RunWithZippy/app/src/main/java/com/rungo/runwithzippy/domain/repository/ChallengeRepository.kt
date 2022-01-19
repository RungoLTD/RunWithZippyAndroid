package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.ChallengeResponse
import com.rungo.runwithzippy.data.model.AccessTokenParam

interface ChallengeRepository {
    suspend fun getAllChallenges(accessToken: AccessTokenParam): ChallengeResponse
}