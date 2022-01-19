package com.rungo.runwithzippy.domain.usecase.store

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.ChallengeRepository
import com.rungo.runwithzippy.domain.repository.ProfileRepository
import com.rungo.runwithzippy.domain.repository.StatisticRepository
import com.rungo.runwithzippy.domain.repository.StoreRepository
import com.rungo.runwithzippy.utils.Constants

class StoreBuySkinUseCase constructor(
    private val storeRepository: StoreRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<SkinResponse, Any?>(apiErrorHandle) {


    override suspend fun run(params: Any?): SkinResponse {
        return storeRepository.buySkin(params as SkinRequest)
    }
}