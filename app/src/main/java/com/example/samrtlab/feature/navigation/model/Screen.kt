package com.example.samrtlab.feature.navigation.model

sealed class Screen(
    val route: String
) {
    object LaunchScreen : Screen(route = "launch_screen")
    object Onboard : Screen(route = "onboard_screen")
    object ChangeMail : Screen(route = "change_mail_screen")
    object ConfirmEmail : Screen(route = "confirm_email_screen")
    object CreatePassword : Screen(route = "create_password_screen")
    object CreateMap : Screen(route = "create_map_screen")
    object MainScreen : Screen(route = "main_screen")
    object CartScreen : Screen(route = "cart_screen")
}
