package com.frommetoyou.interchallenge.feature_marvel.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = primaryColor,
    background = backgroundColor,
    onBackground = Color.White,
    primaryVariant = primaryDarkColor,
    onPrimary = primaryTextColor
)

@Composable
fun InterChallengeTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}