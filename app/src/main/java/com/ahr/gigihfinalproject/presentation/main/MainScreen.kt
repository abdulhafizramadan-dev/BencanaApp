package com.ahr.gigihfinalproject.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.util.emptyString
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun MainScreen() {

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val isExpanded = remember(key1 = scaffoldState.bottomSheetState.currentValue) {
        scaffoldState.bottomSheetState.currentValue == BottomSheetValue.Expanded
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    var state by remember { mutableStateOf(MainHeaderSectionState.DEFAULT) }
    var value by remember { mutableStateOf("") }
    val dummyNames = listOf("Abdul", "Hafiz", "Ramadan")
    val predictions = remember(key1 = value) {
        if (value.isNotEmpty()) {
            dummyNames.filter { it.contains(value, true) }
        } else emptyList()
    }

    val disasterItems =
        listOf("Banjir", "Gempa Bumi", "Kebakaran", "Kabut", "Angin Topan", "Gunung Meletus")
    var selectedDisaster by remember { mutableStateOf(emptyString()) }

    BottomSheetScaffold(
        sheetContent = {
            MainSheetContent(
                isExpanded = isExpanded,
                onCloseIconClicked = { scope.launch { scaffoldState.bottomSheetState.collapse() } }
            )
        },
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetPeekHeight = 396.dp,
        scaffoldState = scaffoldState,
    ) {
        MainContent(
            modifier = Modifier.fillMaxSize(),
            state = state,
            onStateChanged = { state = it },
            placeholder = stringResource(R.string.hint_search_here),
            value = value,
            onValueChanged = { value = it },
            onSettingsIconClicked = {},
            onDoneClicked = { keyboardController?.hide() },
            onItemClicked = { keyboardController?.hide() },
            predictions = predictions,
            selectedDisaster = selectedDisaster,
            disasterItems = disasterItems,
            onDisasterClicked = { selectedDisaster = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    state: MainHeaderSectionState = MainHeaderSectionState.DEFAULT,
    onStateChanged: (MainHeaderSectionState) -> Unit = {},
    placeholder: String = emptyString(),
    value: String = emptyString(),
    onValueChanged: (String) -> Unit = {},
    onSettingsIconClicked: () -> Unit = {},
    onDoneClicked: () -> Unit = {},
    onItemClicked: (String) -> Unit = {},
    predictions: List<String> = emptyList(),
    selectedDisaster: String = emptyString(),
    disasterItems: List<String> = emptyList(),
    onDisasterClicked: (String) -> Unit = {},
) {
    BoxWithConstraints(modifier = modifier) {
        Box(
            modifier = Modifier
                .height((maxHeight + 32.dp) - 396.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Dummy Maps",
                style = MaterialTheme.typography.titleLarge
            )
        }

        RowMainDisasterChip(
            items = disasterItems,
            selected = selectedDisaster,
            onChipClicked = onDisasterClicked,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp)
                .padding(top = 58.dp)
        )
        MainHeaderSection(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            state = state,
            onStateChanged = onStateChanged,
            placeholder = placeholder,
            value = value,
            onValueChanged = onValueChanged,
            onSettingsIconClicked = onSettingsIconClicked,
            onDoneClicked = onDoneClicked,
            onItemClicked = onItemClicked,
            predictions = predictions
        )
    }
}