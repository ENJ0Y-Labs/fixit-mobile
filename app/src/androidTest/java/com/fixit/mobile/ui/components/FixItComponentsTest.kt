package com.fixit.mobile.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.fixit.mobile.ui.theme.FixItTheme
import org.junit.Rule
import org.junit.Test

class FixItComponentsTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun primaryButtonDisplaysItsLabel() {
        composeRule.setContent {
            FixItTheme { FixItPrimaryButton(text = "Continue", onClick = {}) }
        }
        composeRule.onNodeWithText("Continue").assertIsDisplayed()
    }

    @Test
    fun statusBadgeDisplaysStatusText() {
        composeRule.setContent {
            FixItTheme { FixItStatusBadge(status = "Awaiting bids") }
        }
        composeRule.onNodeWithText("Awaiting bids").assertIsDisplayed()
    }
}
