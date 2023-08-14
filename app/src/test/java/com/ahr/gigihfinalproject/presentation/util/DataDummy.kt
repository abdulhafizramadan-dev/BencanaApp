package com.ahr.gigihfinalproject.presentation.util

import com.ahr.gigihfinalproject.domain.model.DisasterFilterTimePeriod
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterProperties
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.presentation.main.DisasterGeometryState
import com.ahr.gigihfinalproject.presentation.main.MainHeaderSectionState
import com.ahr.gigihfinalproject.presentation.main.MainScreenUiState
import com.ahr.gigihfinalproject.util.emptyString

object DataDummy {
    fun generateDummyMainScreenUiState(): MainScreenUiState {
        return MainScreenUiState(
            mainHeaderSectionState = MainHeaderSectionState.DEFAULT,
            provinceSearchQuery = "Cari disini",
            provinceSearchHint = "Cari disini",
            selectedProvince = generateProvinces()[0],
            selectedDisasterFilter = generateDisasterTypes()[0],
            selectedDisasterTimePeriod = generateDisasterFilterTimePeriods()[0],
            disasterGeometryState = DisasterGeometryState.Loading,
            disasterFilters = emptyList(),
            disasterFilterTimePeriods = emptyList(),
            provinceList = emptyList(),
            latestDisastersInformation = emptyList(),
            isDisasterFilterTimePeriodShow = false
        )
    }

    fun generateDisasterTypes(): List<DisasterType> {
        return listOf(
            DisasterType("Banjir", "banjir"),
            DisasterType("Gempa", "gempa"),
        )
    }

    fun generateDisasterFilterTimePeriods(): List<DisasterFilterTimePeriod> {
        return listOf(
            DisasterFilterTimePeriod(timeSecond = 10000, name = "Hari ini", selected = false),
            DisasterFilterTimePeriod(timeSecond = 100000, name = "Satu hari yang lalu", selected = false),
        )
    }

    fun generateProvinces(): List<Province> {
        return listOf(
            Province("Jawa Tengah", "jawa-tengah"),
            Province("Jawa Barat", "jawa-barat"),
            Province("Jawa Timur", "jawa-timur"),
        )
    }

    fun generateDisasterGeometries(): List<DisasterGeometry> {
        return listOf(
            DisasterGeometry(
                coordinates = listOf(1.0, 0.1),
                type = "Point",
                disasterProperties = DisasterProperties(
                    imageUrl = emptyString(),
                    disasterType = "flood",
                    createdAt = "2023-08-13T04:51:21.080Z",
                    pkey = "322048",
                    source = "grasp",
                    text = "Trainer CUCUN SUPREDI",
                    status = "confirmed"
                )
            ),
        )
    }

}