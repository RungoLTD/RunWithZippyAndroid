package com.rungo.runwithzippy.data.repository

import com.rungo.runwithzippy.data.model.AccessTokenParam
import com.rungo.runwithzippy.api.ApiService
import com.rungo.runwithzippy.data.model.AccessTokenParamInit
import com.rungo.runwithzippy.data.model.InitialConfigResponse
import com.rungo.runwithzippy.domain.repository.InitialConfigRepository

class InitialConfigRepositoryImpl(private val apiService: ApiService): InitialConfigRepository {

    override suspend fun getInitialConfig(accessToken: AccessTokenParamInit): InitialConfigResponse {
        return apiService.getInitialConfig(accessToken)
    }
}