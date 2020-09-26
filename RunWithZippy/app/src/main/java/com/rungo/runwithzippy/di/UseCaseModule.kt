package com.rungo.runwithzippy.di

import com.rungo.runwithzippy.domain.usecase.GetChallengesUseCase
import com.rungo.runwithzippy.domain.usecase.GetTrainingUseCase
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithEmailUseCase
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithFacebookUseCase
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithGoogleUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { AuthWithEmailUseCase(get(), get(), get()) }

    single { AuthWithGoogleUseCase(get(), get()) }

    single { AuthWithFacebookUseCase(get(), get()) }

    single { GetChallengesUseCase(get(), get()) }

    single { GetTrainingUseCase(get(), get()) }
}