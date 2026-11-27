package com.fixit.mobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val FixItBackground = Color(0xFFF7F7F7)
val FixItSurface = Color.White
val FixItInk = Color(0xFF171717)
val FixItText = Color(0xFF222222)
val FixItMuted = Color(0xFF707070)
val FixItBorder = Color(0xFFE8E8E8)

private val FixItColors = lightColorScheme(
    primary = FixItInk,
    onPrimary = Color.White,
    background = FixItBackground,
    surface = FixItSurface,
    onSurface = FixItText,
    secondary = FixItMuted,
    outline = FixItBorder
)

@Composable
fun FixItTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = FixItColors, content = content)
}
