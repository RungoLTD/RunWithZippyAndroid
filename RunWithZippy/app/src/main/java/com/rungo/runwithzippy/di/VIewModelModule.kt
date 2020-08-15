package com.rungo.runwithzippy.di

import com.rungo.runwithzippy.presentation.features.challenge.ChallengeViewModel
import com.rungo.runwithzippy.presentation.features.login.LoginViewModel
import com.rungo.runwithzippy.presentation.features.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { ChallengeViewModel(get(), get()) }
}