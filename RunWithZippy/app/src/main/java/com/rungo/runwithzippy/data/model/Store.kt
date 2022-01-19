package com.rungo.runwithzippy.data.model


data class GetSkinsRequest(
    val accessToken: String,
    val appVersion: String
)

data class GetSkinsResponse(
    val success: Boolean,
    val data: List<Skins>,
)

data class SkinRequest(
    val accessToken: String,
    val skinIdentifier: String
)

data class PaymentRequest(
    val accessToken: String,
    val paymentIdentifier: String,
    val transactionId: String
)

data class SkinResponse(
    val success: String,
    val error: String,
    val msg: String,
)