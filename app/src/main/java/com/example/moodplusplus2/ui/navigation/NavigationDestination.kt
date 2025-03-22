package com.example.moodplusplus2.ui.navigation

interface NavigationDestination {
    val route: String
    val titleRes: Int
}

interface BottomNavigationDestination: NavigationDestination {
    val iconRes: Int
}