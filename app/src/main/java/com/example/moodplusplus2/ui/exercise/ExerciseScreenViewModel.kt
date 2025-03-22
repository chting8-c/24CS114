package com.example.moodplusplus2.ui.exercise

import android.content.Context
import android.os.CountDownTimer
import android.os.Vibrator
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodplusplus2.ui.exercise.ExerciseTimeFormat.timeFormat
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ExerciseViewModel(): ViewModel() {

    private var timer: CountDownTimer? = null
    private val selectedMinute = TimeUnit.MINUTES.toMillis(5)
    private val selectedSecond = TimeUnit.SECONDS.toMillis(0)

    val initialTime = selectedMinute + selectedSecond
    val timeLeft = mutableStateOf(initialTime)
    val timeInterval = 1000L

    val timerText = mutableStateOf(timeLeft.value.timeFormat())

    val isCounting = mutableStateOf(false)

    fun startTimer() = viewModelScope.launch {
        isCounting.value = true
        timer = object: CountDownTimer(timeLeft.value, timeInterval) {
            override fun onTick(currentTimeLeft: Long) {
                timerText.value = currentTimeLeft.timeFormat()
                timeLeft.value = currentTimeLeft
            }

            override fun onFinish() {
                isCounting.value = false
                timerText.value = initialTime.timeFormat()
                timeLeft.value = initialTime
            }
        }.start()
    }

    fun stopTimer() = viewModelScope.launch {
        isCounting.value = false
        timer?.cancel()
    }

    fun resetTimer() = viewModelScope.launch {
        isCounting.value = false
        timer?.cancel()
        timeLeft.value = initialTime
        timerText.value = timeLeft.value.timeFormat()
    }

    fun increaseTimerTime() = viewModelScope.launch {
        if(isCounting.value == false && timeLeft.value<1800000L) {
            timeLeft.value += 60000L
            timerText.value = timeLeft.value.timeFormat()
        }
    }

    fun decreaseTimerTime() = viewModelScope.launch {
        if(isCounting.value == false && timeLeft.value>60000L) {
            timeLeft.value -= 60000L
            timerText.value = timeLeft.value.timeFormat()
        }
    }
}

object ExerciseTimeFormat {
    private const val format = "%02d:%02d"

    fun Long.timeFormat(): String = String.format(
        format,
        TimeUnit.MILLISECONDS.toMinutes(this)%60,
        TimeUnit.MILLISECONDS.toSeconds(this)%60
    )
}