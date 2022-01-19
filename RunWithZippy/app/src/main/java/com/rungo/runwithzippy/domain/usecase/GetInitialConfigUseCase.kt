package com.rungo.runwithzippy.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.ChallengeRepository
import com.rungo.runwithzippy.domain.repository.InitialConfigRepository
import com.rungo.runwithzippy.domain.repository.ProfileRepository
import com.rungo.runwithzippy.utils.Constants

class GetInitialConfigUseCase constructor(
    private val configRepository: InitialConfigRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<InitialConfigResponse, Any?>(apiErrorHandle) {

    var initial_config = MutableLiveData<Config>().apply { value = null }

    override suspend fun run(params: Any?): InitialConfigResponse {
        print(params)
        return configRepository.getInitialConfig(params as AccessTokenParamInit)
    }
}