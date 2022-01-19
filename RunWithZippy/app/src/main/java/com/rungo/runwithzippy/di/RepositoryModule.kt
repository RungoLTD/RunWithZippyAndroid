package com.rungo.runwithzippy.di

import com.rungo.runwithzippy.api.ApiService
import com.rungo.runwithzippy.data.repository.*
import com.rungo.runwithzippy.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {

    single { createAuthRepository(get()) }
    single { createChallengeRepository(get()) }
    single { createTrainingRepository(get())}
    single { createRegistrationRepository(get())}
    single { createProfileRepository(get())}
    single { createInitialConfigRepository(get()) }
    single { createStatisticAddRepository(get()) }
    single { createStoreAddRepository(get()) }
    single { createFriendsRepository(get()) }
    single { createSendFcmTokenRepository(get()) }
}

fun createAuthRepository(apiService: ApiService) : AuthRepository{
    return AuthRepositoryImpl(apiService)
}

fun createChallengeRepository(apiService: ApiService): ChallengeRepository {
    return ChallengeRepositoryImpl(apiService)
}

fun createTrainingRepository(apiService: ApiService): TrainingRepository {
    return TrainingRepositoryImpl(apiService)
}

fun createRegistrationRepository(apiService: ApiService): RegistrationRepository {
    return RegRepositoryImpl(apiService)
}

fun createProfileRepository(apiService: ApiService): ProfileRepository {
    return ProfileRepositoryImpl(apiService)
}

fun createFriendsRepository(apiService: ApiService): FriendsRepository {
    return FriendsRepositoryImpl(apiService)
}

fun createInitialConfigRepository(apiService: ApiService): InitialConfigRepository {
    return InitialConfigRepositoryImpl(apiService)
}

fun createStatisticAddRepository(apiService: ApiService): StatisticRepository {
    return StatisticsRepositoryImpl(apiService)
}

fun createStoreAddRepository(apiService: ApiService): StoreRepository {
    return StoreRepositoryImpl(apiService)
}

fun createSendFcmTokenRepository(apiService: ApiService): FcmTokenRepository {
    return SendFcmTokenRepositoryImpl(apiService)
}