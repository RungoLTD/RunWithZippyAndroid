package com.rungo.runwithzippy.data.model


data class SendFcmToken(
    val accessToken: String,
    val os_code: Int,
    val version_build: Int,
    val version_app: String,
    val fcmToken: String
)

data class FcmTokenResponse(
    val success: Boolean,
    val code: Int
)