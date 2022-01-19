package com.rungo.runwithzippy.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.model.*
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.ChallengeRepository
import com.rungo.runwithzippy.domain.repository.ProfileRepository
import com.rungo.runwithzippy.utils.Constants

class ProfileUpdateUseCase constructor(
    private val profileRepository: ProfileRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<ProfileResponse, Any?>(apiErrorHandle) {

    var profile_data = MutableLiveData<Profile>().apply { value = null }

//    var achi = MutableLiveData<List<Achievements>>().apply { value = null }
    override suspend fun run(params: Any?): ProfileResponse {
        return profileRepository.getProfileUpdate(params as ProfileUpdateRequest)
    }
}