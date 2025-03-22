package com.example.moodplusplus2.data

import androidx.compose.ui.res.stringResource
import com.example.moodplusplus2.R

object LocalExternalResDataProvider {
    val allExternalRes = listOf (
        ExternalRes(
            id = 0,
            title = R.string.chatpoint_title,
            description = R.string.chatpoint_description,
            accessMethod = R.string.chatpoint_access
        ),
        ExternalRes(
            id = 1,
            title = R.string.ha_title,
            description = R.string.ha_description,
            accessMethod = R.string.ha_access
        ),
        ExternalRes(
            id = 2,
            title = R.string.openup_title,
            description = R.string.openup_description,
            accessMethod = R.string.openup_access
        ),
        ExternalRes(
            id = 3,
            title = R.string.sbhk_title,
            description = R.string.sbhk_description,
            accessMethod = R.string.sbhk_access
        ),
        ExternalRes(
            id = 4,
            title = R.string.who_title,
            description = R.string.who_description,
            accessMethod = R.string.who_access
        )
    )

    fun get(id: Int): ExternalRes? {
        return allExternalRes.firstOrNull{it.id==id}
    }

}