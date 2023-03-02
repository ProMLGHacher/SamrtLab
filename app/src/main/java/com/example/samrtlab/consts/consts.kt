package com.example.samrtlab.consts

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun elevation(
    defaultElevation: Dp = 0.dp,
    pressedElevation: Dp = 0.dp,
    disabledElevation: Dp = 0.dp,
    hoveredElevation: Dp = 0.dp,
    focusedElevation: Dp = 0.dp,
): ButtonElevation {
    return ButtonDefaults.elevation(
        defaultElevation = defaultElevation,
        pressedElevation = pressedElevation,
        disabledElevation = disabledElevation,
        hoveredElevation = hoveredElevation,
        focusedElevation = focusedElevation
    )
}