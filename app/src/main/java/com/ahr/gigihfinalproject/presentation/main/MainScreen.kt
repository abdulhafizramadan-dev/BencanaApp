package com.ahr.gigihfinalproject.presentation.main

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.presentation.destinations.SettingsScreenDestination
import com.ahr.gigihfinalproject.util.emptyString
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
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
    val mainScreenUiState by mainViewModel.homeScreenUiState.collectAsState()

    val mainHeaderSectionState = mainScreenUiState.mainHeaderSectionState
    val provinceSearchHint = mainScreenUiState.provinceSearchHint
    val provinceSearchQuery = mainScreenUiState.provinceSearchQuery
    val selectedDisaster = mainScreenUiState.selectedDisasterFilter
    val disasterFilters = mainScreenUiState.disasterFilters
    val disasterReports = mainScreenUiState.latestDisasterInformations
    val provinceList = mainScreenUiState.provinceList

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        mainViewModel.updateProvinceSearchHint(context.getString(R.string.hint_search_here))
    }

    val disasterMarker = remember { mutableStateListOf<LatLng>() }

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
    val mapsUiSettings = MapUiSettings(
        zoomControlsEnabled = false,
        compassEnabled = true,
        myLocationButtonEnabled = myLocationActive
    )
    val mapsProperties = MapProperties(
        isMyLocationEnabled = myLocationActive
    )

    LaunchedEffect(key1 = disasterReports) {
        if (disasterReports is Resource.Success) {
            val disasterCoordinates = disasterReports.data.map { it.coordinates }
            disasterCoordinates.forEach { coordinates ->
                val position = LatLng(
                    coordinates[1],
                    coordinates[0],
                )
                disasterMarker.add(position)
                boundsBuilder.include(position)
            }
            scope.launch {
                delay(1000L)
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 5)
                )
            }
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

    val onProvinceQueryChanged: (String) -> Unit = {
        val searchHint = it.ifEmpty { context.getString(R.string.hint_search_here) }
        mainViewModel.updateProvinceSearchHint(hint = searchHint)
        mainViewModel.updateProvinceSearchQuery(query = it)
        mainViewModel.searchProvinces(query = it)
    }

    val onSettingsIconClicked: () -> Unit = { navigator.navigate(SettingsScreenDestination()) }

    val onDoneImeClicked: () -> Unit = { keyboardController?.hide() }

    val onProvinceClicked: (Province) -> Unit = {
        keyboardController?.hide()
        mainViewModel.updateProvinceSearchHint(hint = it.name)
        mainViewModel.updateProvinceSearchQuery(query = it.name)
        mainViewModel.updateMainHeaderSectionState(MainHeaderSectionState.DEFAULT)
    }

    val onDisasterClicked: (DisasterType) -> Unit = {
        mainViewModel.updateSelectedDisasterFilter(it)
        disasterMarker.clear()
    }

    BottomSheetScaffold(
        sheetContent = {
            MainSheetContent(
                isExpanded = isExpanded,
                onCloseIconClicked = { scope.launch { scaffoldState.bottomSheetState.collapse() } },
                latestDisasters = disasterReports
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
            provinceSearchQuery = provinceSearchQuery,
            onProvinceQueryChanged = onProvinceQueryChanged,
            onSettingsIconClicked = onSettingsIconClicked,
            onDoneImeClicked = onDoneImeClicked,
            onProvinceClicked = onProvinceClicked,
            provinceList = provinceList,
            selectedDisaster = selectedDisaster,
            disasterItems = disasterFilters,
            onDisasterClicked = onDisasterClicked,
            cameraPositionState = cameraPositionState,
            mapsProperties = mapsProperties,
            mapsUiSettings = mapsUiSettings,
            latLngLists = disasterMarker
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
    provinceSearchQuery: String = emptyString(),
    onProvinceQueryChanged: (String) -> Unit = {},
    onSettingsIconClicked: () -> Unit = {},
    onDoneImeClicked: () -> Unit = {},
    provinceList: List<Province> = emptyList(),
    onProvinceClicked: (Province) -> Unit = {},
    selectedDisaster: DisasterType? = null,
    disasterItems: List<DisasterType> = emptyList(),
    onDisasterClicked: (DisasterType) -> Unit = {},
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    mapsProperties: MapProperties = MapProperties(),
    mapsUiSettings: MapUiSettings = MapUiSettings(),
    latLngLists: List<LatLng> = emptyList(),
) {

    BoxWithConstraints(modifier = modifier) {
        GoogleMap(
            modifier = Modifier
                .height((maxHeight + 32.dp) - 396.dp)
                .fillMaxWidth(),
            properties = mapsProperties,
            uiSettings = mapsUiSettings,
            cameraPositionState = cameraPositionState,
            contentPadding = PaddingValues(start = 6.dp, top = 146.dp, end = 6.dp, bottom = 24.dp)

        ) {
            latLngLists.forEach {
                Marker(state = MarkerState(position = it))
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
            query = provinceSearchQuery,
            onProvinceQueryChanged = onProvinceQueryChanged,
            onSettingsIconClicked = onSettingsIconClicked,
            onDoneImeClicked = onDoneImeClicked,
            onProvinceClicked = onProvinceClicked,
            provinceList = provinceList
        )
    }
}
