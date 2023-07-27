package com.ahr.gigihfinalproject.presentation.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator = EmptyDestinationsNavigator
) {

    val settingsViewModel = hiltViewModel<SettingsViewModel>()
    val userTheme by settingsViewModel.userTheme.collectAsState()

    Scaffold(
        topBar = {
            SettingTopAppBar(title = stringResource(R.string.label_settings)) {
                navigator.navigateUp()
            }
        }
    ) { paddingValues ->
        SettingsContent(
            modifier = Modifier.padding(paddingValues),
            userTheme = userTheme,
            updateUserTheme = settingsViewModel::updateUserTheme
        )
    }
}




@Preview
@Composable
fun PreviewSettingsScreen() {
    GigihFinalProjectTheme {
        Surface {

        }
    }
}
