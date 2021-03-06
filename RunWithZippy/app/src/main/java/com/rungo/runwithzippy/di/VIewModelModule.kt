package com.rungo.runwithzippy.di

import com.rungo.runwithzippy.presentation.features.challenge.ChallengeViewModel
import com.rungo.runwithzippy.presentation.features.creationTraining.CreationTrainingViewModel
import com.rungo.runwithzippy.presentation.features.description.DescriptionViewModel
import com.rungo.runwithzippy.presentation.features.login.LoginViewModel
import com.rungo.runwithzippy.presentation.features.running.RunningViewModel
import com.rungo.runwithzippy.presentation.features.splash.SplashViewModel
import com.rungo.runwithzippy.presentation.features.training.TrainingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { ChallengeViewModel(get(), get()) }
    viewModel { TrainingViewModel(get(), get()) }
    viewModel { DescriptionViewModel(get()) }
    viewModel { CreationTrainingViewModel(get()) }
    viewModel { RunningViewModel() }
}