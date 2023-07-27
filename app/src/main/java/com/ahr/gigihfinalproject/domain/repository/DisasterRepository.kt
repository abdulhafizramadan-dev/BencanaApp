package com.ahr.gigihfinalproject.domain.repository

import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface DisasterRepository {

    fun getLatestDisasterInformation(): Flow<Resource<List<DisasterGeometry>>>

    fun getDisasterReportWithFilter(
        province: Province?,
        disasterType: DisasterType?,
    ): Flow<Resource<List<DisasterGeometry>>>

    fun getProvinces(query: String): Flow<List<Province>>

    fun getDisasterFilter(): Flow<List<DisasterType>>

}