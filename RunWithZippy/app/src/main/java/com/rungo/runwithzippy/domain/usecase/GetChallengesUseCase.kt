package com.rungo.runwithzippy.domain.usecase

import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.ChallengeResponse
import com.rungo.runwithzippy.data.model.AccessToken
import com.rungo.runwithzippy.data.model.AccessTokenParam
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.ChallengeRepository
import com.rungo.runwithzippy.utils.Constants

class GetChallengesUseCase constructor(
    private val challengeRepository: ChallengeRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<ChallengeResponse, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): ChallengeResponse {
        return challengeRepository.getAllChallenges(params as AccessTokenParam)
    }
}