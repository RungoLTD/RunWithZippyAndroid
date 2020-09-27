package com.rungo.runwithzippy.presentation.features.description

import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.domain.usecase.GetTrainingUseCase

class DescriptionViewModel(
    private val trainingUseCase: GetTrainingUseCase
) : BaseViewModel() {

    var training = trainingUseCase.training
}