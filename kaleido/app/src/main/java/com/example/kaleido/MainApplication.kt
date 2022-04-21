package com.example.kaleido

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * DaggerHilt REQUIRES you to use a custom Application class
 * which NEEDS to be annotated with @HiltAndroidApp
 */
@HiltAndroidApp
class MainApplication: Application() {
}