package com.example.moodplusplus2

import android.app.Application
import com.example.moodplusplus2.data.AppContainer
import com.example.moodplusplus2.data.AppDataContainer

class MoodApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}