package com.rungo.runwithzippy.di

import com.rungo.runwithzippy.data.remote.ApiService
import com.rungo.runwithzippy.data.repository.AuthRepositoryImpl
import com.rungo.runwithzippy.data.repository.ChallengeRepositoryImpl
import com.rungo.runwithzippy.domain.repository.AuthRepository
import com.rungo.runwithzippy.domain.repository.ChallengeRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { createAuthRepository(get()) }
    single { createChallengeRepository(get()) }
}

fun createAuthRepository(apiService: ApiService) : AuthRepository{
    return AuthRepositoryImpl(apiService)
}

fun createChallengeRepository(apiService: ApiService): ChallengeRepository {
    return ChallengeRepositoryImpl(apiService)
}