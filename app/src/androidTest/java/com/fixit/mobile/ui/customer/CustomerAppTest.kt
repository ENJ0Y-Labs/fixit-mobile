package com.fixit.mobile.ui.customer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.fixit.mobile.ui.theme.FixItTheme
import org.junit.Rule
import org.junit.Test

class CustomerAppTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun customerCanOpenMarketplaceSearchFromHome() {
        composeRule.setContent {
            FixItTheme {
                CustomerApp(onWorkspaceChange = {})
            }
        }

        composeRule.onNodeWithText("Get your home fixed by people you can trust.").assertIsDisplayed()
        composeRule.onNodeWithText("Find a professional").performClick()
        composeRule.onNodeWithText("Discover trusted local help").assertIsDisplayed()
    }

    @Test
    fun customerCanSeeJobFormValidation() {
        composeRule.setContent {
            FixItTheme {
                CustomerApp(onWorkspaceChange = {})
            }
        }

        composeRule.onNodeWithText("Post a new job").performClick()
        composeRule.onNodeWithText("Save demo job request").performClick()
        composeRule.onNodeWithText("Enter a title, category, location and valid budget to continue.").assertIsDisplayed()
    }

    @Test
    fun customerCanOpenMyJobsTab() {
        composeRule.setContent {
            FixItTheme {
                CustomerApp(onWorkspaceChange = {})
            }
        }

        composeRule.onNodeWithText("My Jobs").performClick()
        composeRule.onNodeWithText("Your service requests").assertIsDisplayed()
    }
}
