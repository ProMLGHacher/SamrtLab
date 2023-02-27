package com.example.samrtlab.feature.main.naviagtion.model

import com.example.samrtlab.R

sealed class MainNavScreen(
    val name: String,
    val icon: Int
) {
    object Analyzes : MainNavScreen("Анализы", R.drawable.analyzes)
    object Results : MainNavScreen("Результаты", R.drawable.results)
    object Support : MainNavScreen("Поддержка", R.drawable.support)
    object Profile : MainNavScreen("Профиль", R.drawable.profile)
}

