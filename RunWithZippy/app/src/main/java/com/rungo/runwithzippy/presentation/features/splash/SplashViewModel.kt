package com.rungo.runwithzippy.presentation.features.splash

import com.rungo.runwithzippy.base.BaseViewModel
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.utils.Constants
import timber.log.Timber

class SplashViewModel constructor(
    private val preferenceHelper: PreferenceHelper
): BaseViewModel() {

    fun isAccessTokenAvailable(): Boolean {
        Timber.d("ACCESS TOKEN ${preferenceHelper[Constants.ACCESS_TOKEN]}")
        return preferenceHelper[Constants.ACCESS_TOKEN].isNotEmpty()
    }
}