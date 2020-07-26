package com.rungo.runwithzippy.data.remote

import com.rungo.runwithzippy.utils.Constants

data class AuthWithEmail(
    val pw: String,
    val email: String,
    val authType: String = Constants.emailAuth
)

data class AuthWithGoogle(
    val accessToken: String,
    val authType: String = Constants.googleAuth
)

data class AuthWithFacebook(
    val accessToken: String,
    val authType: String = Constants.facebookAuth
)

data class AuthResponse(
    val success: Boolean,
    val data: Data
)

data class Data(
    val access_token: String
)