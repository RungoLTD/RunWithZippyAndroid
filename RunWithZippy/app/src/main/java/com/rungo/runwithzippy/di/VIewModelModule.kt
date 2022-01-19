package com.rungo.runwithzippy.di

import com.rungo.runwithzippy.presentation.features.challenge.ChallengeViewModel
import com.rungo.runwithzippy.presentation.features.config.FcmTokenModel
import com.rungo.runwithzippy.presentation.features.config.InitialConfigModel
import com.rungo.runwithzippy.presentation.features.creationTraining.CreationTrainingViewModel
import com.rungo.runwithzippy.presentation.features.description.DescriptionViewModel
import com.rungo.runwithzippy.presentation.features.friends.FriendsViewModel
import com.rungo.runwithzippy.presentation.features.login.LoginViewModel
import com.rungo.runwithzippy.presentation.features.main.MainViewModel
import com.rungo.runwithzippy.presentation.features.profile.ProfileViewModel
import com.rungo.runwithzippy.presentation.features.registration.RegistrationViewModel
import com.rungo.runwithzippy.presentation.features.running.RunningViewModel
import com.rungo.runwithzippy.presentation.features.shop.ShopViewModel
import com.rungo.runwithzippy.presentation.features.splash.SplashViewModel
import com.rungo.runwithzippy.presentation.features.training.TrainingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { InitialConfigModel(get(), get()) }
    viewModel { FcmTokenModel(get(), get()) }
    viewModel { ChallengeViewModel(get(), get()) }
    viewModel { TrainingViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get(), get()) }
    viewModel { DescriptionViewModel(get()) }
    viewModel { CreationTrainingViewModel(get()) }
    viewModel { RunningViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { ShopViewModel(get(), get(), get(), get(), get()) }
    viewModel { FriendsViewModel(get(), get()) }

}