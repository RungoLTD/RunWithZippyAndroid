package com.rungo.runwithzippy.data.model

import java.util.*


data class RunningResponse(
    val success: Boolean,
    val data: Statistics
)

data class RunningRequest(
    val accessToken: String,
    val meters: Long,
    val time: Long,
    val maxSpeed: Double,
    val avgSpeed: Double,
    val avgPace: Double,
    val routes: MutableList<LocationModel>,
    val paces: Double,
)

data class RunningUpdateRequest(
    val accessToken: String,
    val statisticId: Int,
    val mood: Int,
    val trainName: String,
    val isSharedInSociety: Boolean
)

data class StepRequest(
    val accessToken: String,
    val stepCount: Int
)

data class LocationModel(
    val lat: Double,
    val lon: Double
)

data class RunningStatisticsListRequest(
    val accessToken: String,
    val page: Int,
)

data class RunningStatisticsViewRequest(
    val accessToken: String,
    val statisticId: Int,
)

data class GetRunningStatisticsResponse(
    val success: Boolean,
    val data: List<RunningStatistic>
)

data class GetRunningStatisticViewResponse(
    val success: Boolean,
    val data: RunningStatistic
)

data class RunningStatistic(
    val id: Int,
    val user_id: Int,
    val meters: Long,
    val time: Long,
    val maxSpeed: Double,
    val avgSpeed: Double,
    val avgPace: Double,
    val paces: String,
    val name: String,
    val created: Date,
    val routes: List<LocationModel>?
)