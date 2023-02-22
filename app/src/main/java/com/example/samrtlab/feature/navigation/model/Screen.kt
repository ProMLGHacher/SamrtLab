package com.example.samrtlab.feature.navigation.model

sealed class Screen(
    val route: String
) {
    object LaunchScreen : Screen(route = "launch_screen")
    object Onboard : Screen(route = "onboard_screen")
}
