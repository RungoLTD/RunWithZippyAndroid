package com.rungo.runwithzippy.domain.usecase.auth

import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.data.model.AuthResponse
import com.rungo.runwithzippy.data.model.AuthWithGoogle
import com.rungo.runwithzippy.domain.repository.AuthRepository

class AuthWithGoogleUseCase constructor(
    private val authRepository: AuthRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<AuthResponse, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): AuthResponse {
        return authRepository.loginWithGoogle(params as AuthWithGoogle)
    }
}