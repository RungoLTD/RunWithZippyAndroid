package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.*

interface StatisticRepository {
    suspend fun getStatisticAdd(runningRequest: RunningRequest): RunningResponse

    suspend fun getStatisticSetStepStats(stepRequest: StepRequest): RunningResponse

    suspend fun getStatisticUpdate(runningUpdateRequest: RunningUpdateRequest): RunningResponse

    suspend fun getRunningStatistics(runningStatisticsListRequest: RunningStatisticsListRequest): GetRunningStatisticsResponse

    suspend fun getRunningStatisticView(runningStatisticsViewRequest: RunningStatisticsViewRequest): GetRunningStatisticViewResponse

    //, meters: RunningRequest, time: RunningRequest, maxSpeed: RunningRequest, avgSpeed: RunningRequest, avgPace: RunningRequest, routes: RunningRequest, paces: RunningRequest
}