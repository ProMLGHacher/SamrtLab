package com.example.samrtlab.feature.navigation.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.samrtlab.feature.launch.vew.LaunchScreen
import com.example.samrtlab.feature.launch.view_model.LaunchScreenViewModel
import com.example.samrtlab.feature.navigation.model.Screen
import com.example.samrtlab.feature.onboard.view.Onboard

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.LaunchScreen.route) {
        composable(Screen.LaunchScreen.route) {
            LaunchScreen(navController = navController)
        }
        composable(Screen.Onboard.route) {
            Onboard()
        }
    }
}