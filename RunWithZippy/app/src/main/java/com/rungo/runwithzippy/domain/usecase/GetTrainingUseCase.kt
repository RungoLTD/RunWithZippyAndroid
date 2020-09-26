package com.rungo.runwithzippy.domain.usecase

import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.AccessTokenParam
import com.rungo.runwithzippy.data.model.TrainingResponse
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.TrainingRepository

class GetTrainingUseCase constructor(
    private val trainingRepository: TrainingRepository,
    apiErrorHandle: ApiErrorHandle
) : BaseUseCase<TrainingResponse, Any>(apiErrorHandle) {

    override suspend fun run(params: Any?): TrainingResponse {
        return trainingRepository.getTrainings(params as AccessTokenParam)
    }
}