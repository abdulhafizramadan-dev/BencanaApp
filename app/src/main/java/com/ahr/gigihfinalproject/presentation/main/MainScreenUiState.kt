package com.ahr.gigihfinalproject.presentation.main

import com.ahr.gigihfinalproject.domain.model.DisasterFilterTimePeriod
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.util.emptyString

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
    val latestDisastersInformation: Resource<List<DisasterGeometry>> = Resource.Idling,
    val isDisasterFilterTimePeriodShow: Boolean = false
)
