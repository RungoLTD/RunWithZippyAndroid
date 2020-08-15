package com.rungo.runwithzippy.domain.usecase.auth

import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.data.model.AuthResponse
import com.rungo.runwithzippy.data.model.AuthWithFacebook
import com.rungo.runwithzippy.domain.repository.AuthRepository

class AuthWithFacebookUseCase constructor(
    private val authRepository: AuthRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<AuthResponse, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): AuthResponse {
        return authRepository.loginWithFacebook(params as AuthWithFacebook)
    }
}