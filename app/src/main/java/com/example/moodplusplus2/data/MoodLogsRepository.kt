package com.example.moodplusplus2.data

import kotlinx.coroutines.flow.Flow

interface MoodLogsRepository {
    fun getAllMoodLogsStream(): Flow<List<MoodLog>>

    fun getMoodLogStream(id: Int): Flow<MoodLog>

    fun getAllSentimentCount(): Flow<List<SentimentCount>>

    suspend fun insertMoodLog(moodLog: MoodLog)

    suspend fun deleteMoodLog(moodLog: MoodLog)

    suspend fun updateMoodLog(moodLog: MoodLog)
}