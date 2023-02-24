package com.example.samrtlab.feature.navigation.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.samrtlab.feature.change_mail.view.ChangeMail
import com.example.samrtlab.feature.comfirm_email.view.ConfirmEmail
import com.example.samrtlab.feature.create_map.view.CreateMap
import com.example.samrtlab.feature.create_password.view.CreatePassword
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
            Onboard {
                navController.navigate(Screen.ChangeMail.route)
            }
        }
        composable(Screen.ChangeMail.route) {
            ChangeMail {
                navController.navigate(Screen.ConfirmEmail.route)
            }
        }
        composable(Screen.ConfirmEmail.route) {
            ConfirmEmail(
                navController
            )
        }
        composable(Screen.CreatePassword.route) {
            CreatePassword(
                navController
            )
        }
        composable(Screen.CreateMap.route) {
            CreateMap(
                navController
            )
        }
    }
}