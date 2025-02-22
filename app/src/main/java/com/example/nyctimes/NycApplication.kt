package com.example.nyctimes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NycApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        Timber.plant(Timber.DebugTree())
    }
}