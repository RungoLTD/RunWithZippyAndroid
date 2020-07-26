package com.rungo.runwithzippy.data.repository

import com.rungo.runwithzippy.data.remote.*
import com.rungo.runwithzippy.domain.repository.AuthRepository

class AuthRepository(private val apiService: ApiService) : AuthRepository {

    override suspend fun loginWithEmail(authWithEmail: AuthWithEmail): AuthResponse {
        return apiService.loginWithEmail(authWithEmail)
    }

    override suspend fun loginWithGoogle(authWithGoogle: AuthWithGoogle): AuthResponse {
        return apiService.loginWithGoogle(authWithGoogle)
    }

    override suspend fun loginWithFacebook(authWithFacebook: AuthWithFacebook): AuthResponse {
        return apiService.loginWithFacebook(authWithFacebook)
    }

}