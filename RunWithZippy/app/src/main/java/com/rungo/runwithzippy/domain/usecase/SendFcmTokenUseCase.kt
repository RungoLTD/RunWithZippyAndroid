package com.rungo.runwithzippy.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.ChallengeRepository
import com.rungo.runwithzippy.domain.repository.FcmTokenRepository
import com.rungo.runwithzippy.domain.repository.InitialConfigRepository
import com.rungo.runwithzippy.domain.repository.ProfileRepository
import com.rungo.runwithzippy.utils.Constants

class SendFcmTokenUseCase constructor(
    private val configRepository: FcmTokenRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<FcmTokenResponse, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): FcmTokenResponse {
        print(params)
        return configRepository.sendFcmToken(params as SendFcmToken)
    }
}