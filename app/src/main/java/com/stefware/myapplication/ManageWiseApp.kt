package com.stefware.myapplication

import kotlin.text.Typography.dagger

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ManageWiseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize app-wide components
    }
}