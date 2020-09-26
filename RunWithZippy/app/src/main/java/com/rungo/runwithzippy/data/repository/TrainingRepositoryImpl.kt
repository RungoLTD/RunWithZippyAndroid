package com.rungo.runwithzippy.data.repository

import com.rungo.runwithzippy.data.model.AccessTokenParam
import com.rungo.runwithzippy.data.model.TrainingResponse
import com.rungo.runwithzippy.data.remote.ApiService
import com.rungo.runwithzippy.domain.repository.TrainingRepository

class TrainingRepositoryImpl(private val apiService: ApiService) : TrainingRepository {

    override suspend fun getTrainings(accessToken: AccessTokenParam): TrainingResponse {
        return apiService.getTrains(accessToken)
    }
}