package ru.narayone.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Главный класс приложения с поддержкой Hilt
 */
@HiltAndroidApp
class NaRayoneApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Здесь можно добавить дополнительную инициализацию
    }
} 