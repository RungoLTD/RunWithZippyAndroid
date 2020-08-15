package com.rungo.runwithzippy

import android.app.Application
import com.rungo.runwithzippy.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        initKoin()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.uprootAll()
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    var newTag = tag
                    if (tag != null) newTag = "RUN WITH ZIPPY: $tag"
                    super.log(priority, newTag, message, t)
                }
            })
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(networkModule)
            modules(appModule)
            modules(repositoryModule)
            modules(viewModelModule)
            modules(useCaseModule)
        }
    }
}