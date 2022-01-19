package com.rungo.runwithzippy.data.model


data class InitialConfigResponse(
    val success: Boolean,
    val data: Config,
    val code: Int
)

data class Config (
    val skins: List<Skins>,
    val user: Profile,
    val cloudCatTexts: List<CatTexts>
)

data class Skins (
    val id: String,
    val title: String,
    val cost: Int,
    val assets_url: String,
    val thumb_url: String,
    val description: String,
    val purchased: Boolean
)

data class CatTexts (
    val id: Int,
    val text: String,
    val type: String,
    val title_en: String,
    val duration: Int
)
