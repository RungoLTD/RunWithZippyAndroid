package com.rungo.runwithzippy.data.repository

import com.rungo.runwithzippy.api.ApiService
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.domain.repository.StoreRepository

class StoreRepositoryImpl(private val apiService: ApiService): StoreRepository {

    override suspend fun getSkins(getSkinsRequest: GetSkinsRequest): GetSkinsResponse {
        return apiService.getSkins(getSkinsRequest)
    }

    override suspend fun buySkin(skinRequest: SkinRequest): SkinResponse {
        return apiService.buySkin(skinRequest)
    }

    override suspend fun applySkin(skinRequest: SkinRequest): SkinResponse {
        return apiService.applySkin(skinRequest)
    }
    
}