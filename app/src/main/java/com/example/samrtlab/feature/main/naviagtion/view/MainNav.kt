package com.example.samrtlab.feature.main.naviagtion.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.samrtlab.feature.main.analyzes.view.Analyzes
import com.example.samrtlab.feature.main.naviagtion.model.MainNavScreen

@Composable
fun MainNav(
    appNavController: NavController
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
                            Icon(
                                painter = painterResource(id = screen.icon),
                                contentDescription = null
                            )
                        },
                        label = { Text(screen.name) },
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
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = MainNavScreen.Analyzes.name,
            Modifier.padding(innerPadding)
        ) {
            composable(MainNavScreen.Analyzes.name) {
                Analyzes(mainNavController = navController, appNavController = appNavController)
            }
            composable(MainNavScreen.Results.name) {

            }
            composable(MainNavScreen.Support.name) {

            }
            composable(MainNavScreen.Profile.name) {

            }
        }
    }
}