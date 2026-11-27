package com.fixit.mobile.ui.admin

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class AdminAppTest {
    @get:Rule val composeRule = createComposeRule()

    @Test
    fun adminPreviewRequiresDemoGateAndOpensDashboard() {
        composeRule.setContent { AdminApp(onWorkspaceChange = {}) }
        composeRule.onNodeWithText("Local demo access").assertIsDisplayed()
        composeRule.onNodeWithText("Sign in to admin preview").performClick()
        composeRule.onNodeWithText("Platform overview").assertIsDisplayed()
        composeRule.onNodeWithText("LOCAL DEMO").assertIsDisplayed()
    }

    @Test
    fun adminNavigationOpensSettingsAndAccount() {
        composeRule.setContent { AdminApp(onWorkspaceChange = {}) }
        composeRule.onNodeWithText("Sign in to admin preview").performClick()
        composeRule.onNodeWithText("More").performClick()
        composeRule.onNodeWithText("Open Settings").performClick()
        composeRule.onNodeWithText("Settings").assertIsDisplayed()
        composeRule.onNodeWithText("More").performClick()
        composeRule.onNodeWithText("Open Admin account").performClick()
        composeRule.onNodeWithText("Admin account").assertIsDisplayed()
    }
}
