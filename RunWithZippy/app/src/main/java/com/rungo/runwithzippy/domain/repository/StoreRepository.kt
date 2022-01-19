package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.*

interface StoreRepository {
    suspend fun getSkins(runningRequest: GetSkinsRequest): GetSkinsResponse

    suspend fun buySkin(skinRequest: SkinRequest): SkinResponse

    suspend fun applySkin(skinRequest: SkinRequest): SkinResponse

    suspend fun payment(skinRequest: PaymentRequest): SkinResponse

    //, meters: RunningRequest, time: RunningRequest, maxSpeed: RunningRequest, avgSpeed: RunningRequest, avgPace: RunningRequest, routes: RunningRequest, paces: RunningRequest
}