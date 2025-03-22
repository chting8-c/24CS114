package com.example.moodplusplus2.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineMoodLogsRepository(private val moodLogDao: MoodLogDao): MoodLogsRepository {
    override fun getAllMoodLogsStream(): Flow<List<MoodLog>> = moodLogDao.getAllMoodLogs()

    override fun getMoodLogStream(id: Int): Flow<MoodLog> = moodLogDao.getMoodLog(id)

    override fun getAllSentimentCount(): Flow<List<SentimentCount>> = moodLogDao.getAllSentimentCounts()

    override suspend fun insertMoodLog(moodLog: MoodLog) = moodLogDao.insert(moodLog)

    override suspend fun deleteMoodLog(moodLog: MoodLog) = moodLogDao.delete(moodLog)

    override suspend fun updateMoodLog(moodLog: MoodLog) = moodLogDao.update(moodLog)
}