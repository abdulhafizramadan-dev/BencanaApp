package com.ahr.gigihfinalproject.domain.usecase

import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {

    fun getLatestDisasterInformation(): Flow<Resource<List<DisasterGeometry>>>

    fun getDisasterReport(
        disasterType: DisasterType
    ): Flow<Resource<List<DisasterGeometry>>>

    fun getProvinces(query: String): Flow<List<Province>>

    fun getDisasterFilter(): Flow<List<DisasterType>>

}