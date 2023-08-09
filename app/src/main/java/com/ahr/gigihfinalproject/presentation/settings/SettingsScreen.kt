package com.ahr.gigihfinalproject.presentation.settings

import android.Manifest
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.UserTheme
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@ExperimentalPermissionsApi
@Destination
@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator = EmptyDestinationsNavigator
) {

    val settingsViewModel = hiltViewModel<SettingsViewModel>()
    val userTheme by settingsViewModel.userTheme.collectAsState()
    val userNotificationTmaMonitoringPreference by settingsViewModel.userNotificationTmaMonitoringPreference.collectAsState()

    val notificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(key1 = userNotificationTmaMonitoringPreference) {
        if (userNotificationTmaMonitoringPreference) {
            settingsViewModel.runFirstTmaMonitoring()
            settingsViewModel.runPeriodicTmaMonitoring()
        } else {
            settingsViewModel.cancelNotificationBaseWaterWorker()
        }
    }

    val requestNotificationPermission: () -> Unit = {
        notificationPermission.launchPermissionRequest()
    }

    val updateUserNotificationBaseWaterSetting: (Boolean) -> Unit = {
        if (notificationPermission.status == PermissionStatus.Granted) {
            settingsViewModel.updateUserNotificationBaseWaterSetting(it)
        } else {
            requestNotificationPermission()
        }
    }

    Scaffold(
        topBar = {
            SettingTopAppBar(title = stringResource(R.string.label_settings)) {
                navigator.navigateUp()
            }
        },
        modifier = Modifier.statusBarsPadding()
    ) { paddingValues ->
        SettingsContent(
            modifier = Modifier.padding(paddingValues),
            userTheme = userTheme,
            updateUserTheme = settingsViewModel::updateUserTheme,
            userNotificationBaseWaterSetting = userNotificationTmaMonitoringPreference,
            updateUserNotificationBaseWaterSetting = updateUserNotificationBaseWaterSetting,
        )
    }
}




@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewSettingsScreen() {
    GigihFinalProjectTheme {
        Surface {
            Scaffold(
                topBar = {
                    SettingTopAppBar(title = stringResource(R.string.label_settings)) {}
                }
            ) { paddingValues ->
                SettingsContent(
                    modifier = Modifier.padding(paddingValues),
                    userTheme = UserTheme.Light,
                    updateUserTheme = {},
                    userNotificationBaseWaterSetting = false,
                    updateUserNotificationBaseWaterSetting = {}
                )
            }
        }
    }
}
