package com.rungo.runwithzippy.presentation.features.creationTraining

import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.domain.usecase.GetTrainingUseCase
import timber.log.Timber

class CreationTrainingViewModel(
    trainingUseCase: GetTrainingUseCase
) : BaseViewModel() {

    var options = trainingUseCase.options

    init {
        Timber.d("MY OPTIONS ${options.value}")
    }
}