package com.ahr.gigihfinalproject.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme

@ExperimentalMaterial3Api
@Composable
fun SettingTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackClicked: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.desc_back_to_home_screen)
                )
            }
        },
        modifier = modifier.shadow(4.dp),
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewSettingTopAppBar() {
    GigihFinalProjectTheme {
        Surface(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {
            Scaffold(
                topBar = {
                    SettingTopAppBar(title = stringResource(R.string.label_settings)) {

                    }
                },
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues))
            }

        }
    }
}
