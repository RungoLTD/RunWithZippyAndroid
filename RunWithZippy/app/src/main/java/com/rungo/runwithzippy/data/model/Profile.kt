package com.rungo.runwithzippy.data.model


data class ProfileResponse(
    val success: Boolean,
    val data: Profile,
//    val availLangs: List<String>
)

data class ProfileStatisticsResponse(
    val success: Boolean,
    val data: Statistics,
//    val availLangs: List<String>
)

data class AnimationResponse(
    val success: Boolean,
    val data: Statistics,
//    val availLangs: List<String>
)

data class Profile(
    val id: Int,
    val auth_type: String,
    val name: String,
    val skin: String,
    val access_token: String,
    val social_user_id: String,
    val email: String,
    val avatar: String,
    val mood: Int,
    val mood_updated_date: String,
    val last_active_date: String,
    val last_notified_date: String,
    val distance_recognize_type: String,
    val step_count_updated_day: String,
    val step_count_normal: Int,
    val train_completed_updated: String,
    val first_chat_completed: Int,
    val first_chat_step: Int,
    val timezone: String,
    val mood_notify_sended: String,
    val mood_notify_time: String,
    val trophy_name: String,
    val rebuke_name: String,
    val train_multiplayer: Int,
    val seven_days_mood_notified: String,
    val height: Int,
    val weight: Int,
    val created: String,
    val is_reviewed_in_store: Int,
    val train_buyed_date: String,
    val lang: String,
    val fcm_token: String,
//    val statistics: List<Statistics>,
    val coins: Int,
//    val achievements: List<List<Achievements>>
)

data class ProfileUpdateRequest(
    val accessToken: String,
    val name: String,
    val avatar: String,
    val trophyName: String,
    val rebukeName: String,
    val height: Int,
    val weight: Int,
)

data class Statistics (
    val distance: Long,
    val trainCount: Int,
    val avgSpeed: String,
    val avgPace: String,
    val statisticId: Int
)

data class Achievements (
    val id: Int,
    val title_ru: String,
    val description_ru: String,
    val title_en: String,
    val description_en: String,
    val image_url: String,
    val cat_mood_append: Int,
    val coins_count: Int,
    val color: String,
)
