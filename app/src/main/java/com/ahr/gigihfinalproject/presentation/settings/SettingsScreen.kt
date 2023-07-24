package com.ahr.gigihfinalproject.presentation.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = {
            SettingTopAppBar(title = stringResource(R.string.label_settings)) {

            }
        }
    ) { paddingValues ->
        SettingsContent(modifier = Modifier.padding(paddingValues))
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
