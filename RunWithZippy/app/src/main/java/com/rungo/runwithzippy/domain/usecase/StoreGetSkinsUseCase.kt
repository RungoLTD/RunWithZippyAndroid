package com.rungo.runwithzippy.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.ChallengeRepository
import com.rungo.runwithzippy.domain.repository.ProfileRepository
import com.rungo.runwithzippy.domain.repository.StatisticRepository
import com.rungo.runwithzippy.domain.repository.StoreRepository
import com.rungo.runwithzippy.utils.Constants

class StoreGetSkinsUseCase constructor(
    private val storeRepository: StoreRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<GetSkinsResponse, Any?>(apiErrorHandle) {

    var skins_data = MutableLiveData<List<Skins>>().apply { value = null }

    override suspend fun run(params: Any?): GetSkinsResponse {
        return storeRepository.getSkins(params as GetSkinsRequest)
    }
}