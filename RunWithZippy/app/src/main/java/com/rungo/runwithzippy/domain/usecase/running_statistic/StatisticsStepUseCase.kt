package com.rungo.runwithzippy.domain.usecase.running_statistic

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.StatisticRepository

class StatisticsStepUseCase constructor(
    private val statisticRepository: StatisticRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<RunningResponse, Any?>(apiErrorHandle) {

    var statistic_data = MutableLiveData<RunningResponse>().apply { value = null }

//    var achi = MutableLiveData<List<Achievements>>().apply { value = null }
    override suspend fun run(params: Any?): RunningResponse {
        return statisticRepository.getStatisticSetStepStats(params as StepRequest)
    //,  params as RunningRequest, params as RunningRequest, params as RunningRequest, params as RunningRequest,  params as RunningRequest, params as RunningRequest, params as RunningRequest
    }
}