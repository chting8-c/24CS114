package com.example.moodplusplus2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moodplusplus2.MoodBottomAppBar
import com.example.moodplusplus2.ui.exercise.ExerciseDestination
import com.example.moodplusplus2.ui.exercise.ExerciseScreen
import com.example.moodplusplus2.ui.external.ExternalResDestination
import com.example.moodplusplus2.ui.external.ExternalResScreen
import com.example.moodplusplus2.ui.moodlog.LogDetailDestination
import com.example.moodplusplus2.ui.moodlog.LogDetailScreen
import com.example.moodplusplus2.ui.moodlog.LogHomeDestination
import com.example.moodplusplus2.ui.moodlog.LogHomeScreen
import com.example.moodplusplus2.ui.moodlog.LogInputDestination
import com.example.moodplusplus2.ui.moodlog.LogInputScreen
import com.example.moodplusplus2.ui.logSummary.LogSummaryDestination
import com.example.moodplusplus2.ui.logSummary.LogSummaryScreen

@Composable
fun MoodNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val bottomNavigationBar : @Composable () -> Unit = {
        MoodBottomAppBar(
        navigateToHome = {navController.navigate(LogHomeDestination.route)},
        navigateToSummary = {navController.navigate(LogSummaryDestination.route)},
        navigateToExercise = {navController.navigate(ExerciseDestination.route)},
        navigateToExternalRes = {navController.navigate(ExternalResDestination.route)}
        )
    }
    NavHost(
        navController = navController,
        startDestination = LogHomeDestination.route,
        modifier = modifier
    ) {
        composable(route = LogHomeDestination.route) {
            LogHomeScreen(
                navigateToMoodLogInput = {navController.navigate(LogInputDestination.route)},
                navigateToMoodLogUpdate = {
                    navController.navigate("${LogDetailDestination.route}/${it}")
                },
                botNavBar = bottomNavigationBar
            )
        }
        composable(route = LogInputDestination.route) {
            LogInputScreen(
                navigationBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()}
            )
        }
        composable(
            route = LogDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(LogDetailDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            LogDetailScreen(
                navigateBack = {navController.navigateUp()}
            )
        }
        composable(route = LogSummaryDestination.route) {
            LogSummaryScreen(botNavBar = bottomNavigationBar)
        }
        composable(route = ExerciseDestination.route) {
            ExerciseScreen(botNavBar = bottomNavigationBar)
        }
        composable(route = ExternalResDestination.route) {
            ExternalResScreen(botNavBar = bottomNavigationBar)
        }
    }
}