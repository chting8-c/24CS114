package com.example.moodplusplus2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.moodplusplus2.R

@Entity(tableName = "moodLogs")
@TypeConverters(SentimentTypeConverters::class)
class MoodLog (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val sentiment: Sentiment,
    val note: String
)

sealed class Sentiment(val icon: Int) {
    data object Satisfied : Sentiment(icon = R.drawable.sentiment_satisfied)
    data object  Excited: Sentiment(icon = R.drawable.sentiment_excited)
    data object  Neutral: Sentiment(icon = R.drawable.sentiment_neutral)
    data object  Sad: Sentiment(icon = R.drawable.sentiment_sad)
    data object  Stressed: Sentiment(icon = R.drawable.sentiment_stressed)
}

class SentimentTypeConverters {
    @TypeConverter
    fun fromSentiment(sentiment: Sentiment): Int {
        return sentiment.icon
    }

    @TypeConverter
    fun toSentiment(icon: Int): Sentiment {
        return when (icon) {
            R.drawable.sentiment_satisfied -> Sentiment.Satisfied
            R.drawable.sentiment_excited -> Sentiment.Excited
            R.drawable.sentiment_neutral -> Sentiment.Neutral
            R.drawable.sentiment_sad -> Sentiment.Sad
            R.drawable.sentiment_stressed -> Sentiment.Stressed
            else -> throw IllegalArgumentException("Unknown icon: $icon")
        }
    }
}