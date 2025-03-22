package com.example.moodplusplus2.data

import android.content.Context

interface AppContainer {
    val moodLogsRepository: MoodLogsRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val moodLogsRepository: MoodLogsRepository by lazy {
        OfflineMoodLogsRepository(MoodDatabase.getDatabase(context).moodLogDao())
    }
}