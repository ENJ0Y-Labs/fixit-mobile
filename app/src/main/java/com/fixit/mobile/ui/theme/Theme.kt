package com.fixit.mobile.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Values are ported from fixit-frontend/shared/fixit-design-system.css.
val FixItBackground = Color(0xFFF8F9FF)
val FixItSurface = Color(0xFFFFFFFF)
val FixItSurfaceSoft = Color(0xFFEFF4FF)
val FixItSurfaceContainer = Color(0xFFE5EEFF)
val FixItInk = Color(0xFF0B1C30)
val FixItText = Color(0xFF0B1C30)
val FixItMuted = Color(0xFF526174)
val FixItBorder = Color(0xFFE2E8F0)
val FixItBorderStrong = Color(0xFFBCCAC0)
val FixItPrimary = Color(0xFF006948)
val FixItPrimaryHover = Color(0xFF00855D)
val FixItPrimarySoft = Color(0xFFE8F7EF)
val FixItSuccess = Color(0xFF006948)
val FixItSuccessSoft = Color(0xFFE8F7EF)
val FixItWarning = Color(0xFF825100)
val FixItWarningSoft = Color(0xFFFFF3DF)
val FixItDanger = Color(0xFFBA1A1A)
val FixItDangerSoft = Color(0xFFFFDAD6)
val FixItInfo = Color(0xFF285CA8)
val FixItInfoSoft = Color(0xFFEAF2FF)

private val FixItColors = lightColorScheme(
    primary = FixItPrimary,
    onPrimary = Color.White,
    primaryContainer = FixItPrimarySoft,
    onPrimaryContainer = FixItInk,
    secondary = FixItMuted,
    onSecondary = Color.White,
    background = FixItBackground,
    onBackground = FixItText,
    surface = FixItSurface,
    onSurface = FixItText,
    surfaceVariant = FixItSurfaceSoft,
    onSurfaceVariant = FixItMuted,
    error = FixItDanger,
    onError = Color.White,
    errorContainer = FixItDangerSoft,
    outline = FixItBorder,
    outlineVariant = FixItBorderStrong
)

private val FixItShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun FixItTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FixItColors,
        typography = Typography(),
        shapes = FixItShapes,
        content = content
    )
}
