package com.example.samrtlab.feature.main.naviagtion.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.samrtlab.R
import com.example.samrtlab.feature.main.analyzes.view.Analyzes
import com.example.samrtlab.feature.main.naviagtion.model.MainNavScreen
import com.example.samrtlab.feature.main.profile.view.Profile
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalGlideComposeApi
@ExperimentalPagerApi
@ExperimentalSnapperApi
@Composable
fun MainNav(
    appNavController: NavController,
    setSheetState: (state: ModalBottomSheetValue) -> Unit,
    setSheetContent: (@Composable (() -> Unit)) -> Unit
) {
    val items = listOf(
        MainNavScreen.Analyzes,
        MainNavScreen.Results,
        MainNavScreen.Support,
        MainNavScreen.Profile,
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        selectedContentColor = Color(0xFF1A6FEE),
                        unselectedContentColor = Color(0xFFB8C1CC),
                        icon = {
                            Column(horizontalAlignment = CenterHorizontally, modifier = Modifier.padding(8.dp)) {
                                Icon(
                                    painter = painterResource(id = screen.icon),
                                    contentDescription = null,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(screen.name, maxLines = 1, fontSize = 10.sp)
                            }
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.name } == true,
                        onClick = {
                            navController.navigate(screen.name) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = MainNavScreen.Analyzes.name,
            Modifier.padding(innerPadding)
        ) {
            composable(MainNavScreen.Analyzes.name) {
                Analyzes(mainNavController = navController, appNavController = appNavController, setSheetState = setSheetState, setSheetContent = setSheetContent)
            }
            composable(MainNavScreen.Results.name) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = Modifier.fillMaxSize(0.5f))
                }
            }
            composable(MainNavScreen.Support.name) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = null, modifier = Modifier.fillMaxSize(0.5f))
                }
            }
            composable(MainNavScreen.Profile.name) {
                Profile(
                    appNavController = appNavController
                )
            }
        }
    }
}