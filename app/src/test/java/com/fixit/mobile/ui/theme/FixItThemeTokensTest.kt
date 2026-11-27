package com.fixit.mobile.ui.theme

import androidx.compose.ui.graphics.toArgb
import org.junit.Assert.assertEquals
import org.junit.Test

class FixItThemeTokensTest {
    @Test
    fun brandPaletteMatchesFrontendDesignSystem() {
        assertEquals(0xFFF8F9FF.toInt(), FixItBackground.toArgb())
        assertEquals(0xFF006948.toInt(), FixItPrimary.toArgb())
        assertEquals(0xFF0B1C30.toInt(), FixItInk.toArgb())
        assertEquals(0xFFE2E8F0.toInt(), FixItBorder.toArgb())
    }
}
