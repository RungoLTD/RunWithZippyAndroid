package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.AccessToken

interface TrainingRepository {
    suspend fun getTrainings(accessToken: AccessToken)
}