package com.rungo.runwithzippy.domain.usecase.auth

import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.data.remote.AuthResponse
import com.rungo.runwithzippy.data.remote.AuthWithEmail
import com.rungo.runwithzippy.domain.repository.AuthRepository

class AuthWithEmailUseCase constructor(
    private val authRepository: AuthRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<AuthResponse, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): AuthResponse {
        return authRepository.loginWithEmail(params as AuthWithEmail)
    }
}