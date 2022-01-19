package com.rungo.runwithzippy.data.model

import com.rungo.runwithzippy.utils.Constants

data class RegWithEmail(
    val email: String,
    val os_code: Int,
    val version_build: Int,
    val version_app: String,
    val fcmToken: String,
)

data class RegResponse(
    val success: Boolean
)