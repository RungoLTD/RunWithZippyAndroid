package com.rungo.runwithzippy.di

import com.rungo.runwithzippy.data.remote.ApiService
import com.rungo.runwithzippy.data.repository.AuthRepositoryImpl
import com.rungo.runwithzippy.data.repository.ChallengeRepositoryImpl
import com.rungo.runwithzippy.data.repository.TrainingRepositoryImpl
import com.rungo.runwithzippy.domain.repository.AuthRepository
import com.rungo.runwithzippy.domain.repository.ChallengeRepository
import com.rungo.runwithzippy.domain.repository.TrainingRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { createAuthRepository(get()) }
    single { createChallengeRepository(get()) }
    single { createTrainingRepository(get())}
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