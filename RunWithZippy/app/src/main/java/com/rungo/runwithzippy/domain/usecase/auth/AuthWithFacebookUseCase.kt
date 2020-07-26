package com.rungo.runwithzippy.domain.usecase.auth

import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.data.remote.AuthResponse
import com.rungo.runwithzippy.data.remote.AuthWithFacebook
import com.rungo.runwithzippy.data.remote.AuthWithGoogle
import com.rungo.runwithzippy.domain.repository.AuthRepository

class AuthWithFacebookUseCase constructor(
    private val authRepository: AuthRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<AuthResponse, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): AuthResponse {
        return authRepository.loginWithFacebook(params as AuthWithFacebook)
    }
}