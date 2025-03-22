package com.example.moodplusplus2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moodplusplus2.ui.navigation.MoodNavHost

@Composable
fun MoodApp(navController: NavHostController = rememberNavController()) {
    MoodNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color(0xFFFFFF99)),
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodBottomAppBar(
    navigateToHome: () -> Unit = {},
    navigateToSummary: () -> Unit = {},
    navigateToExercise: () -> Unit = {},
    navigateToExternalRes: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        actions = {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = navigateToHome
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.bottom_mood_log),
                        contentDescription = "Log Home",
                    )
                }
                IconButton(
                    onClick = navigateToSummary
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.bottom_summarize),
                        contentDescription = "Log Summary",
                    )
                }
                IconButton(
                    onClick = navigateToExercise
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.bottom_self_improvement),
                        contentDescription = "Exercise",
                    )
                }
                IconButton(
                    onClick = navigateToExternalRes
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.bottom_bookmark),
                        contentDescription = "ExternalRes",
                    )
                }
            }
        }
    )
}