package com.ahr.gigihfinalproject.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.gigihfinalproject.domain.model.DisasterFilterTimePeriod
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.domain.model.Resource
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

    private fun getDisasterFilterTimePeriodPreference() {
        viewModelScope.launch {
            homeUseCase.getDisasterFilterTimePeriodPreference().collect {
                if (it != null) {
                    updateSelectedDisasterFilterTimePeriod(it)
                    getDisasterFilterTimePeriods(it)
                    getDisasterReportWithFilter()
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
        val selectedProvince = _homeScreenUiState.value.selectedProvince
        val selectedDisasterFilter = _homeScreenUiState.value.selectedDisasterFilter
        val selectedDisasterTimePeriod = _homeScreenUiState.value.selectedDisasterTimePeriod

        viewModelScope.launch {
            homeUseCase.getDisasterReportWithFilter(
                timePeriod = selectedDisasterTimePeriod,
                province = selectedProvince,
                disasterType = selectedDisasterFilter
            ).collect { result ->
                when (result) {
                    Resource.Idling -> {}
                    Resource.Loading -> _homeScreenUiState.value = _homeScreenUiState.value.copy(
                        disasterGeometryState = DisasterGeometryState.Loading,
                        latestDisastersInformation = emptyList()
                    )
                    is Resource.Error -> _homeScreenUiState.value = _homeScreenUiState.value.copy(
                        disasterGeometryState = DisasterGeometryState.Error,
                        latestDisastersInformation = result.data ?: emptyList()
                    )
                    is Resource.Success -> _homeScreenUiState.value = _homeScreenUiState.value.copy(
                        disasterGeometryState = DisasterGeometryState.Success,
                        latestDisastersInformation = result.data
                    )
                }
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