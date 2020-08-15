package com.rungo.runwithzippy.domain.repository

import com.rungo.runwithzippy.data.model.AuthResponse
import com.rungo.runwithzippy.data.model.AuthWithEmail
import com.rungo.runwithzippy.data.model.AuthWithFacebook
import com.rungo.runwithzippy.data.model.AuthWithGoogle

interface AuthRepository {

    suspend fun loginWithEmail(authWithEmail: AuthWithEmail): AuthResponse

    suspend fun loginWithGoogle(authWithGoogle: AuthWithGoogle): AuthResponse

    suspend fun loginWithFacebook(authWithFacebook: AuthWithFacebook): AuthResponse
}