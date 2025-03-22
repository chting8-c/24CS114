package com.example.moodplusplus2.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodLogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(moodLog: MoodLog)

    @Update
    suspend fun update(moodLog: MoodLog)

    @Delete
    suspend fun delete(moodLog: MoodLog)

    @Query("SELECT * from moodlogs WHERE id = :id")
    fun getMoodLog(id: Int): Flow<MoodLog>

    @Query("SELECT * from moodlogs ORDER BY date DESC")
    fun getAllMoodLogs(): Flow<List<MoodLog>>

    //@Query("SELECT sentiment, COUNT(*) AS count from moodLogs GROUP BY sentiment") //Bar postions mismatch with label
    @Query("SELECT s.sentimentId AS sentiment, COUNT(m.sentiment) AS count FROM (SELECT 2130968602 AS sentimentId UNION SELECT 2130968603 UNION SELECT 2130968604 UNION SELECT 2130968605 UNION SELECT 2130968606) s LEFT JOIN moodlogs m ON s.sentimentId = m.sentiment GROUP BY s.sentimentId")
    fun getAllSentimentCounts():Flow<List<SentimentCount>>
}

data class SentimentCount(
    @ColumnInfo(name = "sentiment")val sentimentID: Int,
    @ColumnInfo(name = "count")val count: Int
)
