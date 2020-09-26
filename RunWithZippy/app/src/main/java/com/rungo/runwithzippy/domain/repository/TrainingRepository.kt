package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.AccessTokenParam
import com.rungo.runwithzippy.data.model.TrainingResponse

interface TrainingRepository {
    suspend fun getTrainings(accessToken: AccessTokenParam): TrainingResponse
}