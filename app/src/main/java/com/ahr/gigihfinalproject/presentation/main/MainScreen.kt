package com.ahr.gigihfinalproject.presentation.main

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.DisasterFilterTimePeriod
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.domain.model.UserTheme
import com.ahr.gigihfinalproject.presentation.destinations.SettingsScreenDestination
import com.ahr.gigihfinalproject.presentation.settings.SettingsViewModel
import com.ahr.gigihfinalproject.util.emptyString
import com.ahr.gigihfinalproject.util.toDisasterTimeFormat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.option.OptionDialog
import com.maxkeppeler.sheets.option.models.DisplayMode
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionConfig
import com.maxkeppeler.sheets.option.models.OptionSelection
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@SuppressLint("MissingPermission")
@RootNavGraph(start = true)
@Destination
@ExperimentalMaterial3Api
@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class)
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    navigator: DestinationsNavigator = EmptyDestinationsNavigator,
) {

    val mainViewModel = hiltViewModel<MainViewModel>()
    val settingsViewModel = hiltViewModel<SettingsViewModel>()

    var isDarkMode by remember { mutableStateOf(false) }

    val mainScreenUiState by mainViewModel.homeScreenUiState.collectAsState()
    val userTheme by settingsViewModel.userTheme.collectAsState()

    val mainHeaderSectionState = mainScreenUiState.mainHeaderSectionState
    val provinceSearchHint = mainScreenUiState.provinceSearchHint
    val provinceSearchQuery = mainScreenUiState.provinceSearchQuery
    val selectedProvince = mainScreenUiState.selectedProvince
    val selectedDisaster = mainScreenUiState.selectedDisasterFilter
    val disasterFilterTimePeriods = mainScreenUiState.disasterFilterTimePeriods
    val disasterFilters = mainScreenUiState.disasterFilters
    val disasterGeometryState = mainScreenUiState.disasterGeometryState
    val disasterReports = mainScreenUiState.latestDisastersInformation
    val provinceList = mainScreenUiState.provinceList

    val disasterTimePeriodOptions = disasterFilterTimePeriods.map { Option(titleText = it.name, selected = it.selected) }

    val hint = stringResource(id = R.string.hint_search_here)

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    val optionDialogState = rememberUseCaseState()

    LaunchedEffect(key1 = userTheme) {
        isDarkMode = when (userTheme) {
            UserTheme.Dark -> true
            else -> false
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (selectedProvince == null) {
            scope.launch {
                delay(500L)
                mainViewModel.updateProvinceSearchHint(context.getString(R.string.hint_search_here))
            }
        }
    }

    var myLocationActive by remember {
        mutableStateOf(false)
    }

    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val fusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val boundsBuilder: LatLngBounds.Builder by remember {
        mutableStateOf(LatLngBounds.builder())
    }

    val cameraPositionState = rememberCameraPositionState()

    val mapStyleOptions = if (isDarkMode) {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.dark_google_maps_style)
    } else null

    val mapsUiSettings = MapUiSettings(
        zoomControlsEnabled = false,
        compassEnabled = true,
        myLocationButtonEnabled = myLocationActive,
    )
    val mapsProperties = MapProperties(
        isMyLocationEnabled = myLocationActive,
        mapStyleOptions = mapStyleOptions
    )

    LaunchedEffect(key1 = disasterReports) {
        if (disasterGeometryState == DisasterGeometryState.Error) {
            Toast.makeText(context, "Error: Ups ada error nih server kami...", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                myLocationActive = true
            }
        } else {
            locationPermissionState.launchMultiplePermissionRequest()
        }
    }

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    val isExpanded = remember(key1 = scaffoldState.bottomSheetState.currentValue) {
        scaffoldState.bottomSheetState.currentValue == BottomSheetValue.Expanded
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val onCollapseBottomSheetIconClicked: () -> Unit = {
        scope.launch { scaffoldState.bottomSheetState.collapse() }
    }

    val onProvinceQueryChanged: (String) -> Unit = {
        mainViewModel.updateProvinceSearchQuery(query = it)
        mainViewModel.searchProvinces(query = it)
        if (it.isEmpty()) {
            mainViewModel.updateSelectedProvince()
        }
    }

    val onSettingsIconClicked: () -> Unit = { navigator.navigate(SettingsScreenDestination()) }

    val onBackIconClicked: () -> Unit = {
        mainViewModel.updateMainHeaderSectionState(MainHeaderSectionState.DEFAULT)
        if (provinceSearchQuery.isEmpty()) {
            mainViewModel.getDisasterReportWithFilter()
        }
        if (selectedProvince == null) {
            mainViewModel.updateProvinceSearchHint(context.getString(R.string.hint_search_here))
            mainViewModel.updateProvinceSearchQuery("")
            mainViewModel.searchProvinces()
        }
    }

    val onDoneImeClicked: () -> Unit = { keyboardController?.hide() }

    val onProvinceClicked: (Province) -> Unit = {
        keyboardController?.hide()
        mainViewModel.updateProvinceSearchHint(hint = it.name)
        mainViewModel.updateProvinceSearchQuery(query = it.name)
        mainViewModel.updateMainHeaderSectionState(MainHeaderSectionState.DEFAULT)
        mainViewModel.updateSelectedProvince(it)
        mainViewModel.getDisasterReportWithFilter()
    }

    val onDisasterClicked: (DisasterType?) -> Unit = {
        mainViewModel.updateSelectedDisasterFilter(it)
        mainViewModel.getDisasterReportWithFilter()
    }

    val onDisasterTimePeriodIconClicked: () -> Unit = {
        optionDialogState.show()
    }

    val onChooseButtonDisasterTimePeriodClicked: (DisasterFilterTimePeriod?) -> Unit = {
        if (it != null) {
            mainViewModel.updateDisasterFilterTimePeriodPreference(it)
        }
    }

    val onDisasterItemClicked: (DisasterGeometry) -> Unit = {
        scope.launch {
            if (scaffoldState.bottomSheetState.isExpanded) {
                scaffoldState.bottomSheetState.collapse()
                delay(500L)
            }
            val coordinates = it.coordinates
            val position = LatLng(
                coordinates[1],
                coordinates[0],
            )
            cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(position, 20F))
        }
    }

    BackHandler(enabled = mainHeaderSectionState == MainHeaderSectionState.FOCUS) {
        mainViewModel.updateMainHeaderSectionState(MainHeaderSectionState.DEFAULT)
        if (selectedProvince == null) {
            mainViewModel.getDisasterReportWithFilter()
        }
    }

    OptionDialog(
        state = optionDialogState,
        header = Header.Default(
            icon = IconSource(imageVector = Icons.Default.CalendarMonth),
            title = stringResource(R.string.label_header_dialog_filter_disaster_timeperiod)
        ),
        selection = OptionSelection.Single(disasterTimePeriodOptions) { index, _ ->
            onChooseButtonDisasterTimePeriodClicked(disasterFilterTimePeriods[index])
        },
        config = OptionConfig(mode = DisplayMode.LIST)
    )

    BottomSheetScaffold(
        sheetContent = {
            MainSheetScreen(
                isExpanded = isExpanded,
                onCloseIconClicked = onCollapseBottomSheetIconClicked,
                latestDisasters = disasterReports,
                disasterGeometryState = disasterGeometryState,
                onDisasterItemClicked = onDisasterItemClicked
            )
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetPeekHeight = 396.dp,
        scaffoldState = scaffoldState,
    ) {
        MainContent(
            modifier = Modifier.fillMaxSize(),
            state = mainHeaderSectionState,
            onStateChanged = mainViewModel::updateMainHeaderSectionState,
            placeholder = provinceSearchHint,
            hint = hint,
            provinceSearchQuery = provinceSearchQuery,
            onProvinceQueryChanged = onProvinceQueryChanged,
            onDisasterTimePeriodFilterIconClicked = onDisasterTimePeriodIconClicked,
            onSettingsIconClicked = onSettingsIconClicked,
            onBackIconClicked = onBackIconClicked,
            onDoneImeClicked = onDoneImeClicked,
            onProvinceClicked = onProvinceClicked,
            provinceList = provinceList,
            selectedDisaster = selectedDisaster,
            disasterItems = disasterFilters,
            onDisasterClicked = onDisasterClicked,
            cameraPositionState = cameraPositionState,
            mapsProperties = mapsProperties,
            mapsUiSettings = mapsUiSettings,
            focusRequester = focusRequester,
            selectedProvince = selectedProvince,
            disasterReports = disasterReports,
            cameraLatLngBounds = boundsBuilder,
            scope = scope
        )
    }
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    state: MainHeaderSectionState = MainHeaderSectionState.DEFAULT,
    onStateChanged: (MainHeaderSectionState) -> Unit = {},
    placeholder: String = emptyString(),
    hint: String = emptyString(),
    provinceSearchQuery: String = emptyString(),
    onProvinceQueryChanged: (String) -> Unit = {},
    onDisasterTimePeriodFilterIconClicked: () -> Unit = {},
    onSettingsIconClicked: () -> Unit = {},
    onBackIconClicked: () -> Unit = {},
    onDoneImeClicked: () -> Unit = {},
    provinceList: List<Province> = emptyList(),
    onProvinceClicked: (Province) -> Unit = {},
    selectedDisaster: DisasterType? = null,
    disasterItems: List<DisasterType> = emptyList(),
    onDisasterClicked: (DisasterType?) -> Unit = {},
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    mapsProperties: MapProperties = MapProperties(),
    mapsUiSettings: MapUiSettings = MapUiSettings(),
    focusRequester: FocusRequester = FocusRequester.Default,
    selectedProvince: Province? = null,
    disasterReports: List<DisasterGeometry> = emptyList(),
    cameraLatLngBounds: LatLngBounds.Builder = LatLngBounds.Builder(),
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    BoxWithConstraints(modifier = modifier) {
        GoogleMap(
            modifier = Modifier
                .height((maxHeight + 32.dp) - 396.dp)
                .fillMaxWidth(),
            properties = mapsProperties,
            uiSettings = mapsUiSettings,
            cameraPositionState = cameraPositionState,
            contentPadding = PaddingValues(
                start = 6.dp,
                top = 146.dp,
                end = 6.dp,
                bottom = 24.dp
            ),
        ) {
            val disasterCoordinates = disasterReports.map { it.coordinates }
            disasterCoordinates.forEachIndexed { index, coordinates ->
                val disaster = disasterReports[index].disasterProperties
                val position = LatLng(
                    coordinates[1],
                    coordinates[0],
                )
                cameraLatLngBounds.include(position)
                Marker(
                    state = MarkerState(position = position),
                    title = disaster.disasterType,
                    snippet = disaster.createdAt.toDisasterTimeFormat()
                )
            }
            if (disasterReports.isEmpty()) return@GoogleMap
            LaunchedEffect(key1 = disasterReports) {
                scope.launch {
                    delay(1000L)
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngBounds(cameraLatLngBounds.build(), 5)
                    )
                }
            }
        }

        RowMainDisasterChip(
            items = disasterItems,
            selected = selectedDisaster,
            onChipClicked = onDisasterClicked,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp)
                .padding(top = 58.dp),
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
            hint = hint,
            query = provinceSearchQuery,
            onProvinceQueryChanged = onProvinceQueryChanged,
            onSettingsIconClicked = onSettingsIconClicked,
            onDisasterTimePeriodFilterClicked = onDisasterTimePeriodFilterIconClicked,
            onBackIconClicked = onBackIconClicked,
            onDoneImeClicked = onDoneImeClicked,
            onProvinceClicked = onProvinceClicked,
            provinceList = provinceList,
            focusRequester = focusRequester,
            selectedProvince = selectedProvince
        )
    }
}
