package com.rungo.runwithzippy.data.repository

import com.rungo.runwithzippy.api.ApiService
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.repository.StatisticRepository

class StatisticsRepositoryImpl(private val apiService: ApiService): StatisticRepository {

    override suspend fun getStatisticAdd(runningRequest: RunningRequest): RunningResponse {//, meters: RunningRequest, time: RunningRequest, maxSpeed: RunningRequest, avgSpeed: RunningRequest, avgPace: RunningRequest, routes: RunningRequest, paces: RunningRequest
        return apiService.getStatisticAdd(runningRequest) //, meters, time, maxSpeed, avgSpeed, avgPace, routes, paces
    }

    override suspend fun getStatisticSetStepStats(stepRequest: StepRequest): RunningResponse {
        return apiService.getStatisticSetStepStats(stepRequest)
    }

    override suspend fun getStatisticUpdate(runningUpdateRequest: RunningUpdateRequest): RunningResponse {
        return apiService.getStatisticUpdate(runningUpdateRequest) 
    }

    override suspend fun getRunningStatistics(runningStatisticsListRequest: RunningStatisticsListRequest): GetRunningStatisticsResponse {
        return apiService.getRunningStatisticList(runningStatisticsListRequest)
    }

    override suspend fun getRunningStatisticView(runningStatisticsViewRequest: RunningStatisticsViewRequest): GetRunningStatisticViewResponse {
        return apiService.getRunningStatisticView(runningStatisticsViewRequest)
    }

}