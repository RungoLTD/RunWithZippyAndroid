package com.rungo.runwithzippy.data.model

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
    val data: AccessToken?,
    val error: String?
)

data class AccessToken(
    val access_token: String
)

data class AccessTokenParam(
    val accessToken: String
)