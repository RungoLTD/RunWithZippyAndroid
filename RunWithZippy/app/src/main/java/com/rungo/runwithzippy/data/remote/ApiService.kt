package com.rungo.runwithzippy.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/auth")
    suspend fun loginWithEmail(
        @Body authWithEmail: AuthWithEmail
    ): AuthResponse

    @POST("/auth")
    suspend fun loginWithGoogle(
        @Body authWithGoogle: AuthWithGoogle
    ): AuthResponse

    @POST("/auth")
    suspend fun loginWithFacebook(
        @Body authWithFacebook: AuthWithFacebook
    ): AuthResponse
}