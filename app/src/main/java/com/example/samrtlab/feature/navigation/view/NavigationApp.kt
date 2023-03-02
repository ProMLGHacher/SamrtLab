package com.example.samrtlab.feature.navigation.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.samrtlab.feature.change_mail.view.ChangeMail
import com.example.samrtlab.feature.comfirm_email.view.ConfirmEmail
import com.example.samrtlab.feature.create_map.view.CreateMap
import com.example.samrtlab.feature.create_password.view.CreatePassword
import com.example.samrtlab.feature.launch.vew.LaunchScreen
import com.example.samrtlab.feature.main.naviagtion.view.MainNav
import com.example.samrtlab.feature.navigation.model.Screen
import com.example.samrtlab.feature.navigation.view_model.NavViewModel
import com.example.samrtlab.feature.onboard.view.Onboard
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalSnapperApi
@ExperimentalPagerApi
@ExperimentalGlideComposeApi
@Composable
fun NavigationApp(
    viewModel: NavViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when(it) {
                NavViewModel.UiEvent.NoAuth -> {
                    navController.navigate(Screen.LaunchScreen.route)
                }
            }
        }
    }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val content: @Composable (() -> Unit) = {  }
    var customSheetContent by remember { mutableStateOf(content) }
    fun setSheetState(state: ModalBottomSheetValue) {
        if (state == ModalBottomSheetValue.Hidden) {
            scope.launch {
                bottomSheetState.hide()
            }
        } else {
            scope.launch {
                bottomSheetState.show()
            }
        }
    }
    fun setSheetContent(
        content: @Composable (() -> Unit) = {  }
    ) {
        customSheetContent = content
    }
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(10.dp),
        sheetContent = {
            customSheetContent()
        }
    ) {
        NavHost(navController, startDestination = Screen.LaunchScreen.route) {
            composable(Screen.LaunchScreen.route) {
                LaunchScreen(navController = navController)
            }
            composable(Screen.MainScreen.route) {
                MainNav(appNavController = navController,
                    setSheetState = {
                        setSheetState(it)
                    },
                    setSheetContent = {
                        setSheetContent(it)
                    }
                )
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

}

@Composable
fun GetImage(
    ok: (Uri?) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { res: Uri? ->
        ok(res)
    }
}