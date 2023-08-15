package com.ahr.gigihfinalproject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ahr.gigihfinalproject.domain.model.UserTheme
import com.ahr.gigihfinalproject.presentation.NavGraphs
import com.ahr.gigihfinalproject.presentation.settings.SettingsViewModel
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.ramcosta.composedestinations.DestinationsNavHost
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navigateToHomeScreenToSettingsScreen() {
        composeTestRule.setContent {
            GigihFinalProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
        composeTestRule.onNodeWithContentDescription("Go to settings screen").assertExists().performClick()
        composeTestRule.onNodeWithText("Dark Mode").assertExists()
        composeTestRule.onNodeWithText("Notifikasi").assertExists()
        composeTestRule.onNodeWithContentDescription("Back to home screen").assertExists().performClick()
    }

    @Test
    fun testDarkModeSupport() {
        composeTestRule.setContent {
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            val userTheme by settingsViewModel.userTheme.collectAsState()

            LaunchedEffect(key1 = Unit) {
                settingsViewModel.getUserTheme()
            }

            val isDarkTheme = userTheme == UserTheme.Dark
            GigihFinalProjectTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
        composeTestRule.onNodeWithContentDescription("Go to settings screen").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("Checkbox dark mode").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("Back to home screen").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("Go to settings screen").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("Checkbox dark mode").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("Back to home screen").assertExists().performClick()
    }

    @Test
    fun testTmaMonitoringNotification() {
        composeTestRule.setContent {
            GigihFinalProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
        composeTestRule.onNodeWithContentDescription("Go to settings screen").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("Checkbox notification").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("Back to home screen").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("Go to settings screen").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("Checkbox notification").assertExists().performClick()
        composeTestRule.onNodeWithContentDescription("Back to home screen").assertExists().performClick()
    }

}