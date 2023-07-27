package com.ahr.gigihfinalproject.presentation.main

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
    val disasterFilters: List<DisasterType> = emptyList(),
    val provinceList: List<Province> = emptyList(),
    val latestDisasterInformations: Resource<List<DisasterGeometry>> = Resource.Idling
)
