package com.ahr.gigihfinalproject.presentation.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.presentation.destinations.SettingsScreenDestination
import com.ahr.gigihfinalproject.util.emptyString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.launch

private const val TAG = "MainScreen"

@RootNavGraph(start = true)
@Destination
@ExperimentalMaterial3Api
@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    navigator: DestinationsNavigator = EmptyDestinationsNavigator
) {

    val homeViewModel = hiltViewModel<MainViewModel>()
    val disasterReports by homeViewModel.disasterReports.collectAsState()

    LaunchedEffect(key1 = disasterReports) {
        when (disasterReports) {
            Resource.Loading -> Log.d(TAG, "MainScreen: Loading...")
            is Resource.Error -> Log.d(TAG, "MainScreen: Error = ${(disasterReports as Resource.Error<List<DisasterGeometry>>).error.message}")
            is Resource.Success -> Log.d(TAG, "MainScreen: Disaster Reports = $disasterReports")
        }
    }

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val navigateToSettingsScreen = { navigator.navigate(SettingsScreenDestination()) }

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
        listOf(stringResource(R.string.flood),
            stringResource(R.string.earthquake),
            stringResource(R.string.fire),
            stringResource(R.string.haze), stringResource(R.string.wind),
            stringResource(R.string.volcano)
        )
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
            onSettingsIconClicked = navigateToSettingsScreen,
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