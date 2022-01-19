package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.*

interface InitialConfigRepository {
    suspend fun getInitialConfig(accessToken: AccessTokenParamInit): InitialConfigResponse
}