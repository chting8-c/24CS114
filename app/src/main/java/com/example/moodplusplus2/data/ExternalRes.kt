package com.example.moodplusplus2.data

import androidx.annotation.StringRes

data class ExternalRes(
    val id: Int,
    @StringRes val title: Int = -1,
    @StringRes val description: Int = -1,
    @StringRes val accessMethod: Int = -1
)
