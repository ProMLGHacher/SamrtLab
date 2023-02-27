package com.example.samrtlab.feature.launch.vew

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.samrtlab.R
import com.example.samrtlab.feature.launch.model.LaunchScreenState
import com.example.samrtlab.feature.launch.view_model.LaunchScreenViewModel
import com.example.samrtlab.feature.navigation.model.Screen

@Composable
fun LaunchScreen(
    viewModel: LaunchScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = viewModel.state.value) {
        when (viewModel.state.value) {
            LaunchScreenState.Loading -> {

            }
            LaunchScreenState.Rejected -> {
                navController.navigate(Screen.ChangeMail.route)
            }
            LaunchScreenState.Success -> {
                navController.navigate(Screen.CreatePassword.route)
            }
            LaunchScreenState.First -> {
                navController.navigate(Screen.Onboard.route)
            }
        }
    }
    Image(
        painter = painterResource(id = R.drawable.launch),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}