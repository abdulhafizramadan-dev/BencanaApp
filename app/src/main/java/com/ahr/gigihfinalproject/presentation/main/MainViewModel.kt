package com.ahr.gigihfinalproject.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.gigihfinalproject.domain.model.DisasterFilterTimePeriod
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.domain.usecase.HomeUseCase
import com.ahr.gigihfinalproject.util.emptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _homeScreenUiState = MutableStateFlow(MainScreenUiState())
    val homeScreenUiState get() = _homeScreenUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getDisasterFilters()
            searchProvinces()
            delay(2000L)
            getDisasterFilterTimePeriodPreference()
//            getLatestDisastersInformation()
        }
    }

    private fun getDisasterFilters() {
        viewModelScope.launch {
            homeUseCase.getDisasterFilter().collect {
                homeUseCase.getDisasterFilter().collect {
                    _homeScreenUiState.value = _homeScreenUiState.value.copy(
                        disasterFilters = it
                    )
                }
            }
        }
    }

    private fun getLatestDisastersInformation(
        timePeriod: DisasterFilterTimePeriod
    ) {
        viewModelScope.launch {
            homeUseCase.getLatestDisasterInformation(timePeriod = timePeriod).collect {
                _homeScreenUiState.value = _homeScreenUiState.value.copy(
                    latestDisastersInformation = it
                )
            }
        }
    }

    private fun getDisasterFilterTimePeriodPreference() {
        viewModelScope.launch {
            homeUseCase.getDisasterFilterTimePeriodPreference().collect {
                getDisasterFilterTimePeriods(it)
                if (it != null) {
                    getLatestDisastersInformation(timePeriod = it)
                }
            }
        }
    }

    private fun getDisasterFilterTimePeriods(selectedDisasterFilterTimePeriod: DisasterFilterTimePeriod? = null) {
        viewModelScope.launch {
            homeUseCase.getDisasterTimePeriodFilter(selectedDisasterFilterTimePeriod).collect {
                _homeScreenUiState.value = _homeScreenUiState.value.copy(
                    disasterFilterTimePeriods = it
                )
            }
        }
    }

    fun getDisasterReportWithFilter() {
        viewModelScope.launch {
            val selectedProvince = _homeScreenUiState.value.selectedProvince
            val selectedDisasterFilter = _homeScreenUiState.value.selectedDisasterFilter
            val selectedDisasterTimePeriod = _homeScreenUiState.value.selectedDisasterTimePeriod
            homeUseCase.getDisasterReportWithFilter(timePeriod = selectedDisasterTimePeriod, province = selectedProvince, disasterType = selectedDisasterFilter, ).collect {
                _homeScreenUiState.value = _homeScreenUiState.value.copy(
                    latestDisastersInformation = it
                )
            }
        }
    }

    fun updateDisasterFilterTimePeriodPreference(disasterFilterTimePeriod: DisasterFilterTimePeriod) {
        viewModelScope.launch {
            homeUseCase.updateDisasterFilterTimePeriodPreference(disasterFilterTimePeriod)
        }
    }

    fun updateMainHeaderSectionState(state: MainHeaderSectionState) {
        _homeScreenUiState.value = _homeScreenUiState.value.copy(
            mainHeaderSectionState = state
        )
    }

    fun updateProvinceSearchHint(hint: String) {
        _homeScreenUiState.value = _homeScreenUiState.value.copy(
            provinceSearchHint = hint,
        )
    }

    fun updateProvinceSearchQuery(query: String) {
        _homeScreenUiState.value = _homeScreenUiState.value.copy(
            provinceSearchQuery = query
        )
    }

    fun updateSelectedProvince(province: Province? = null) {
        _homeScreenUiState.value = _homeScreenUiState.value.copy(
            selectedProvince = province
        )
    }

    fun updateSelectedDisasterFilterTimePeriod(disasterFilterTimePeriod: DisasterFilterTimePeriod? = null) {
        _homeScreenUiState.value = _homeScreenUiState.value.copy(
            selectedDisasterTimePeriod = disasterFilterTimePeriod
        )
    }

    fun updateSelectedDisasterFilter(disasterType: DisasterType? = null) {
        _homeScreenUiState.value = _homeScreenUiState.value.copy(
            selectedDisasterFilter = disasterType
        )
    }

    @OptIn(FlowPreview::class)
    fun searchProvinces(query: String = emptyString()) {
        viewModelScope.launch {
            homeUseCase.getProvinces(query)
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest {
                    _homeScreenUiState.value = _homeScreenUiState.value.copy(
                        provinceList = it
                    )
                }
        }
    }



}