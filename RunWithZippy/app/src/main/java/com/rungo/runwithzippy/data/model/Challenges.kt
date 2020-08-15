package com.rungo.runwithzippy.data.model

data class ChallengeResponse (
    val success : Boolean,
    val data : List<Challenge>
)

data class Challenge (
    val isActive : Boolean,
    val userData : String,
    val id : Int,
    val title_ru : String,
    val description_ru : String,
    val title_en : String,
    val description_en : String,
    val distance : Int,
    val durationTime : Int,
    val reward : Int,
    val is_week : Int,
    val active_image_url : String,
    val disabled_image_url : String
)