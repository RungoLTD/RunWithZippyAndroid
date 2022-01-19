package com.rungo.runwithzippy.domain.usecase.running_statistic

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.StatisticRepository

class StatisticsGetUseCase constructor(
    private val statisticRepository: StatisticRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<GetRunningStatisticsResponse, Any?>(apiErrorHandle) {

    var statistics_list = MutableLiveData<List<RunningStatistic>>().apply { value = null }

//    var achi = MutableLiveData<List<Achievements>>().apply { value = null }
    override suspend fun run(params: Any?): GetRunningStatisticsResponse {
        return statisticRepository.getRunningStatistics(params as RunningStatisticsListRequest)
    //,  params as RunningRequest, params as RunningRequest, params as RunningRequest, params as RunningRequest,  params as RunningRequest, params as RunningRequest, params as RunningRequest
    }
}