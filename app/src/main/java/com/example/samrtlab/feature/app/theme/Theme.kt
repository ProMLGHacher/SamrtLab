package com.example.samrtlab.feature.app.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable



private val LightColorPalette = lightColors(
    primary = blue,
    primaryVariant = Purple700,
    secondary = Teal200


)

@Composable
fun SamrtLabTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}