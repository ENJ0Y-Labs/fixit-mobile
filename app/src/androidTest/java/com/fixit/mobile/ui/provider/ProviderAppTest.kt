package com.fixit.mobile.ui.provider

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ProviderAppTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun dashboardOpensAvailableJobsAndJobDetails() {
        composeRule.setContent { ProviderApp(onWorkspaceChange = {}) }
        composeRule.onNodeWithText("Available Jobs").assertIsDisplayed().performClick()
        composeRule.onNodeWithText("Repair leaking kitchen sink").assertIsDisplayed()
        composeRule.onNodeWithText("View job and quote").performClick()
        composeRule.onNodeWithText("Submit a quote").assertIsDisplayed()
    }

    @Test fun acceptedJobsExposeProgressActions() {
        composeRule.setContent { ProviderApp(onWorkspaceChange = {}) }
        composeRule.onNodeWithText("My Jobs").assertIsDisplayed().performClick()
        composeRule.onNodeWithText("Start work").assertIsDisplayed()
    }
}
