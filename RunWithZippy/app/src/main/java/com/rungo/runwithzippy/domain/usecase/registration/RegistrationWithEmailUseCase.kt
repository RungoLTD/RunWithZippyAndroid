package com.rungo.runwithzippy.domain.usecase.registration

import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.model.RegResponse
import com.rungo.runwithzippy.data.model.RegWithEmail
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.domain.repository.RegistrationRepository
import com.rungo.runwithzippy.utils.Constants

class RegistrationWithEmailUseCase constructor(
    private val regRepository: RegistrationRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<RegResponse, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): RegResponse {
        return regRepository.registrationWithEmail(params as RegWithEmail)
    }
}