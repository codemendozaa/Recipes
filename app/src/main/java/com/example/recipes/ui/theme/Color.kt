package com.example.recipes.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFFD8D8D8)
val DarkGray = Color(0xFF2A2A2A)

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFf1D1D1D)

val Colors.topAppBarContentColor
    @Composable
    get() = if(isLight) Color.White else LightGray

val Colors.topAppBarBackgroundColor
    @Composable
    get() = if(isLight) Purple500 else Color.Black

val Colors.textColor
    @Composable
    get() = if(isLight) Color.Black else LightGray

val Colors.cardBackgroundColor
    @Composable
    get() = if(isLight) Color.White else Color.Black.copy(alpha = 0.2f)