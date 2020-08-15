package com.rungo.runwithzippy.domain.usecase.auth

import com.rungo.runwithzippy.base.BaseUseCase
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.data.remote.ApiErrorHandle
import com.rungo.runwithzippy.data.model.AuthResponse
import com.rungo.runwithzippy.data.model.AuthWithEmail
import com.rungo.runwithzippy.domain.repository.AuthRepository
import com.rungo.runwithzippy.utils.Constants

class AuthWithEmailUseCase constructor(
    private val sharedPreferences: PreferenceHelper,
    private val authRepository: AuthRepository,
    apiErrorHandle: ApiErrorHandle?
) : BaseUseCase<AuthResponse, Any?>(apiErrorHandle) {

    override suspend fun run(params: Any?): AuthResponse {
        return authRepository.loginWithEmail(params as AuthWithEmail)
    }

    fun setAccessToken(authResponse: AuthResponse) {
        authResponse.data?.accessToken?.let { sharedPreferences.put(Constants.ACCESS_TOKEN, it) }
    }
}