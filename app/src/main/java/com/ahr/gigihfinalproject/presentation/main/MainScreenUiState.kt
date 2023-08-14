package com.ahr.gigihfinalproject.presentation.main

import com.ahr.gigihfinalproject.domain.model.DisasterFilterTimePeriod
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.util.emptyString

enum class DisasterGeometryState {
    Loading, Success, Error;
}

data class MainScreenUiState(
    val mainHeaderSectionState: MainHeaderSectionState = MainHeaderSectionState.DEFAULT,
    val provinceSearchHint: String = emptyString(),
    val provinceSearchQuery: String = emptyString(),
    val selectedProvince: Province? = null,
    val selectedDisasterFilter: DisasterType? = null,
    val selectedDisasterTimePeriod: DisasterFilterTimePeriod? = null,
    val disasterFilters: List<DisasterType> = emptyList(),
    val disasterFilterTimePeriods: List<DisasterFilterTimePeriod> = emptyList(),
    val provinceList: List<Province> = emptyList(),
    val disasterGeometryState: DisasterGeometryState = DisasterGeometryState.Loading,
    val latestDisastersInformation: List<DisasterGeometry> = emptyList(),
    val isDisasterFilterTimePeriodShow: Boolean = false,
    val isFirstLaunch: Boolean = true
)
