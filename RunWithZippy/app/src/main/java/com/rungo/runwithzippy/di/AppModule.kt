package com.rungo.runwithzippy.di

import android.content.Context
import com.rungo.runwithzippy.App
import com.rungo.runwithzippy.data.local.PreferenceHelper
import com.rungo.runwithzippy.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {

    single {
        PreferenceHelper(
            (androidApplication() as App).getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
        )
    }
}