package com.example.moodplusplus2.data

import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarData

object LocalSentimentCountDataProvider {
    val allSentimentCount = listOf (
        BarData(
            point = Point(1f, 5f, ),
            color = Color(0xFF4CAF50),
            label = "Satisfied"
        ),
        BarData(
            point = Point(2f, 2f, ),
            color = Color(0xFFFF9800),
            label = "Exited"
        ),
        BarData(
            point = Point(3f, 8f, ),
            color = Color(0xFF9E9E9E),
            label = "Neutral"
        ),
        BarData(
            point = Point(4f, 4f, ),
            color = Color(0xFF2196F3),
            label = "Sad"
        ),
        BarData(
            point = Point(5f, 6f),
            color = Color(0xFFFF5252),
            label = "Stressed"
        )

    )
}