package com.rungo.runwithzippy.di

import com.rungo.runwithzippy.domain.usecase.*
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithEmailUseCase
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithFacebookUseCase
import com.rungo.runwithzippy.domain.usecase.auth.AuthWithGoogleUseCase
import com.rungo.runwithzippy.domain.usecase.friends.GetFriendsUseCase
import com.rungo.runwithzippy.domain.usecase.registration.RegistrationWithEmailUseCase
import com.rungo.runwithzippy.domain.usecase.running_statistic.*
import com.rungo.runwithzippy.domain.usecase.store.*
import org.koin.dsl.module

val useCaseModule = module {

    single { AuthWithEmailUseCase(get(), get(), get()) }

    single { AuthWithGoogleUseCase(get(), get()) }

    single { AuthWithFacebookUseCase(get(), get()) }

    single { RegistrationWithEmailUseCase(get(), get()) }

    single { GetChallengesUseCase(get(), get()) }

    single { GetProfileUseCase(get(), get()) }

    single { GetFriendsUseCase(get(), get()) }

    single { ProfileUpdateUseCase(get(), get()) }

    single { GetTrainingUseCase(get(), get()) }

    single { GetProfileStatisticsUseCase(get(), get()) }
    
    single { GetInitialConfigUseCase(get(), get()) }

    single { SendFcmTokenUseCase(get(), get()) }

    single { StatisticsAddUseCase(get(), get()) }

    single { StatisticsUpdateUseCase(get(), get()) }

    single { StatisticsGetUseCase(get(), get()) }

    single { StatisticGetViewUseCase(get(), get()) }

    single { StatisticsStepUseCase(get(), get()) }

    single { StoreBuySkinUseCase(get(), get()) }

    single { StoreGetSkinsUseCase(get(), get()) }

    single { StoreApplySkinUseCase(get(), get()) }

    single { StorePaymentUseCase(get(), get()) }
}