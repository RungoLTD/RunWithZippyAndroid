package com.rungo.runwithzippy.api

import com.rungo.runwithzippy.data.model.*
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

    @POST("/register")
    suspend fun registrationWithEmail(
        @Body regWithEmail: RegWithEmail
    ): RegResponse

    @POST("/v2/getInitalConfig")
    suspend fun getInitialConfig(
        @Body accessTokenParam: AccessTokenParamInit
    ): InitialConfigResponse

    @POST("/sendFcmToken")
    suspend fun sendFcmToken(
        @Body accessTokenParam: SendFcmToken
    ): FcmTokenResponse

    @POST("/challenges/fetch")
    suspend fun getChallenges(
        @Body accessToken: AccessTokenParam
    ): ChallengeResponse

    @POST("/trains/getList")
    suspend fun getTrains(
        @Body accessTokenParam: AccessTokenParam
    ): TrainingResponse

    @POST("/profile/fetch")
    suspend fun getProfile(
        @Body accessTokenParam: AccessTokenParam
    ): ProfileResponse

    @POST("/profile/update")
    suspend fun getProfileUpdate(
        @Body profileUpdateParam: ProfileUpdateRequest
    ): ProfileResponse

    @POST("/profile/totalStatistics")
    suspend fun getProfileStatistics(
        @Body accessTokenParam: AccessTokenParam
    ): ProfileStatisticsResponse

    @POST("/profile/getAnimation")
    suspend fun getAnimation(
        @Body accessTokenParam: AccessTokenParam
    ): AnimationResponse

    @POST("/statistics/add")
    suspend fun getStatisticAdd(
        @Body runningRequest: RunningRequest,
    ): RunningResponse

    @POST("/statistics/update")
    suspend fun getStatisticUpdate(
        @Body runningUpdateRequest: RunningUpdateRequest,
    ): RunningResponse

    @POST("/statistics/get")
    suspend fun getRunningStatisticList(
        @Body runningUpdateListRequest: RunningStatisticsListRequest,
    ): GetRunningStatisticsResponse

    @POST("/statistics/get")
    suspend fun getRunningStatisticView(
        @Body runningUpdateListRequest: RunningStatisticsViewRequest,
    ): GetRunningStatisticViewResponse

    @POST("/statistics/setStepStats")
    suspend fun getStatisticSetStepStats(
        @Body stepRequest: StepRequest
    ): RunningResponse

    @POST("store/getSkins")
    suspend fun getSkins(
        @Body getSkinsRequest: GetSkinsRequest
    ): GetSkinsResponse

    @POST("store/buySkin")
    suspend fun buySkin(
        @Body skinRequest: SkinRequest
    ): SkinResponse

    @POST("store/applySkin")
    suspend fun applySkin(
        @Body skinRequest: SkinRequest
    ): SkinResponse

    @POST("store/payment")
    suspend fun payment(
        @Body skinRequest: SkinRequest
    ): SkinResponse

    @POST("friends/list")
    suspend fun friends_list(
        @Body accessTokenParam: AccessTokenParam
    ): FriendsResponse

//    @Body meters: RunningRequest,
//    @Body time: RunningRequest,
//    @Body maxSpeed: RunningRequest,
//    @Body avgSpeed: RunningRequest,
//    @Body avgPace: RunningRequest,
//    @Body routes: RunningRequest,
//    @Body paces: RunningRequest,
}