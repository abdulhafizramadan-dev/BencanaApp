package com.ahr.gigihfinalproject.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.gigihfinalproject.domain.model.UserTheme
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme
import com.ahr.gigihfinalproject.util.emptyString

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    userTheme: UserTheme = UserTheme.Default,
    updateUserTheme: (UserTheme) -> Unit
) {

    val isDarkMode = userTheme == UserTheme.Dark
    val updateDarkMode: (Boolean) -> Unit = { state ->
        val userTheme = if (state) UserTheme.Dark else UserTheme.Light
        updateUserTheme(userTheme)
    }

    Column(modifier = modifier) {
        SettingItem(
            modifier = Modifier.padding(16.dp),
            title = "Dark Mode",
            subtitle = "Enable dark mode",
            state = isDarkMode,
            onStateChange = updateDarkMode
        )
    }
}

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String = emptyString(),
    subtitle: String = emptyString(),
    state: Boolean = false,
    onStateChange: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(checked = state, onCheckedChange = onStateChange)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.6f
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingItem() {
    GigihFinalProjectTheme {
        Surface {
            var state by remember { mutableStateOf(false) }
            SettingItem(
                modifier = Modifier.padding(16.dp),
                title = "Dark Mode",
                subtitle = "Enable dark mode",
                state = state,
                onStateChange = { state = it }
            )
        }
    }
}