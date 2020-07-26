package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.remote.AuthResponse
import com.rungo.runwithzippy.data.remote.AuthWithEmail
import com.rungo.runwithzippy.data.remote.AuthWithFacebook
import com.rungo.runwithzippy.data.remote.AuthWithGoogle

interface AuthRepository {

    suspend fun loginWithEmail(authWithEmail: AuthWithEmail): AuthResponse

    suspend fun loginWithGoogle(authWithGoogle: AuthWithGoogle): AuthResponse

    suspend fun loginWithFacebook(authWithFacebook: AuthWithFacebook): AuthResponse
}