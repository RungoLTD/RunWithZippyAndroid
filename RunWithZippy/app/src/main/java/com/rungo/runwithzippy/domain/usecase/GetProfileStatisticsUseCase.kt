package com.rungo.runwithzippy.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.ProfileRepository

class GetProfileStatisticsUseCase constructor(
    private val profileRepository: ProfileRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<ProfileStatisticsResponse, Any?>(apiErrorHandle) {

    var statistics = MutableLiveData<Statistics>().apply { value = null }

    override suspend fun run(params: Any?): ProfileStatisticsResponse {
        return profileRepository.getProfileStatistics(params as AccessTokenParam)
    }
}