package com.rungo.runwithzippy.data.model

import com.rungo.runwithzippy.utils.Constants

data class AuthWithEmail(
    val pw: String,
    val email: String,
    val fcmToken: String,
    val os_code: Int,
    val version_build: Int,
    val version_app: String,
    val authType: String = Constants.emailAuth
)

data class AuthWithGoogle(
    val accessToken: String,
    val fcmToken: String,
    val authType: String = Constants.googleAuth
)

data class AuthWithFacebook(
    val accessToken: String,
    val fcmToken: String,
    val authType: String = Constants.facebookAuth
)

data class AuthResponse(
    val success: Boolean,
    val data: AuthData?,
    val error: String?
)

data class AuthData(
    val access_token: String,
    val user_data: Profile
)

data class AccessTokenParam(
    val accessToken: String
)

data class AccessTokenParamInit(
    val accessToken: String,
    val os_code: Int,
    val version_build: Int,
    val version_app: String
)