package com.ahr.gigihfinalproject.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.gigihfinalproject.domain.model.DisasterType
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
            getLatestDisasterInformations()
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

    private fun getLatestDisasterInformations() {
        viewModelScope.launch {
            homeUseCase.getLatestDisasterInformation().collect {
                _homeScreenUiState.value = _homeScreenUiState.value.copy(
                    latestDisasterInformations = it
                )
            }
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

    fun updateSelectedDisasterFilter(disasterType: DisasterType) {
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