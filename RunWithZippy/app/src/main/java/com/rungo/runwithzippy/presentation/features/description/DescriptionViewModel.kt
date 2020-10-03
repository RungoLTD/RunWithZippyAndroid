package com.rungo.runwithzippy.presentation.features.description

import androidx.lifecycle.MutableLiveData
import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.data.model.Options
import com.rungo.runwithzippy.domain.usecase.GetTrainingUseCase

class DescriptionViewModel(
    private val trainingUseCase: GetTrainingUseCase
) : BaseViewModel() {

    var training = trainingUseCase.training

    init {
        trainingUseCase.options.value = training.value?.options
    }
}