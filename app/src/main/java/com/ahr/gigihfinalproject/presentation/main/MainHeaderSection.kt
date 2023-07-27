package com.ahr.gigihfinalproject.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.presentation.component.AutoCompleteTextField
import com.ahr.gigihfinalproject.ui.theme.GigihFinalProjectTheme
import com.ahr.gigihfinalproject.util.emptyString

enum class MainHeaderSectionState {
    FOCUS,
    DEFAULT
}

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun MainHeaderSection(
    modifier: Modifier = Modifier,
    state: MainHeaderSectionState = MainHeaderSectionState.DEFAULT,
    onStateChanged: (MainHeaderSectionState) -> Unit = {},
    placeholder: String = emptyString(),
    query: String = emptyString(),
    onProvinceQueryChanged: (String) -> Unit = {},
    onSettingsIconClicked: () -> Unit = {},
    onDoneImeClicked: () -> Unit = {},
    onProvinceClicked: (Province) -> Unit = {},
    selectedProvince: Province? = null,
    provinceList: List<Province> = emptyList(),
    focusRequester: FocusRequester = FocusRequester.Default,
) {
    Column {
        when (state) {
            MainHeaderSectionState.DEFAULT -> MainHeaderTextFieldDefault(
                modifier,
                onStateChanged,
                placeholder,
                onSettingsIconClicked
            )

            MainHeaderSectionState.FOCUS -> MainHeaderTextFieldActive(
                modifier,
                placeholder,
                query,
                onProvinceQueryChanged,
                onDoneImeClicked,
                onProvinceClicked,
                provinceList,
                focusRequester,
                selectedProvince
            )
        }
    }
}

@Composable
fun MainHeaderTextFieldDefault(
    modifier: Modifier = Modifier,
    onStateChanged: (MainHeaderSectionState) -> Unit = {},
    placeholder: String = emptyString(),
    onSettingsIconClicked: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .shadow(8.dp, shape = CircleShape)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .height(50.dp)
            .fillMaxWidth()
            .clickable { onStateChanged(MainHeaderSectionState.FOCUS) }
    ) {
        Text(
            text = placeholder,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5F)
            ),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        )

        IconButton(
            onClick = onSettingsIconClicked,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = stringResource(R.string.desc_go_to_settings_screen),
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHeaderTextFieldActive(
    modifier: Modifier = Modifier,
    placeholder: String = emptyString(),
    value: String = emptyString(),
    onValueChanged: (String) -> Unit = {},
    onDoneClicked: () -> Unit = {},
    onItemClicked: (Province) -> Unit = {},
    predictions: List<Province>,
    focusRequester: FocusRequester,
    selectedProvince: Province?,
) {
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }
    AutoCompleteTextField(
        modifier = modifier.fillMaxWidth(),
        placeholder = placeholder,
        value = value,
        onValueChanged = onValueChanged,
        onClearClicked = { onValueChanged("") },
        onDoneClicked = onDoneClicked,
        predictions = predictions,
        focusRequester = focusRequester
    ) { province ->
        val container = if (province.code == selectedProvince?.code) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background
        val content = if (province.code == selectedProvince?.code) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground
        Row(
            modifier = Modifier
                .clickable { onItemClicked(province) }
                .fillMaxWidth()
                .background(container)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = province.name, color = content)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewMainHeaderSection() {
    GigihFinalProjectTheme {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            var state by remember { mutableStateOf(MainHeaderSectionState.DEFAULT) }
            var value by remember { mutableStateOf("") }

            MainHeaderSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                state = state,
                onStateChanged = { state = it },
                placeholder = stringResource(R.string.hint_search_here),
                query = value,
                onProvinceQueryChanged = { value = it },
                onSettingsIconClicked = {},
                onDoneImeClicked = { keyboardController?.hide() },
                onProvinceClicked = { keyboardController?.hide() },
                provinceList = emptyList()
            )
        }
    }
}
