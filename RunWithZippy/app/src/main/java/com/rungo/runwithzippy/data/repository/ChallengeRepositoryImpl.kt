package com.rungo.runwithzippy.data.repository

import com.rungo.runwithzippy.data.model.ChallengeResponse
import com.rungo.runwithzippy.data.model.AccessToken
import com.rungo.runwithzippy.data.remote.ApiService
import com.rungo.runwithzippy.domain.repository.ChallengeRepository

class ChallengeRepositoryImpl(private val apiService: ApiService): ChallengeRepository {

    override suspend fun getAllChallenges(accessToken: AccessToken): ChallengeResponse {
        return apiService.getChallenges(accessToken)
    }
}