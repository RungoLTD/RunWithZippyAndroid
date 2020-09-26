package com.rungo.runwithzippy.data.repository

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.data.model.AccessTokenParam
import com.rungo.runwithzippy.data.model.Training
import com.rungo.runwithzippy.data.model.TrainingResponse
import com.rungo.runwithzippy.data.remote.ApiService
import com.rungo.runwithzippy.domain.repository.TrainingRepository

class TrainingRepositoryImpl(private val apiService: ApiService) : TrainingRepository {

    var trainings = MutableLiveData<List<List<Training>>>().apply { value = null }

    override suspend fun getTrainings(accessToken: AccessTokenParam): TrainingResponse {
        return apiService.getTrains(accessToken)
    }
}