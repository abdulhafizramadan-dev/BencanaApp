package com.ahr.gigihfinalproject.domain.repository

import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface DisasterRepository {

    fun getLatestDisasterInformation(): Flow<Resource<List<DisasterGeometry>>>

    fun getDisasterReport(
        disasterType: DisasterType
    ): Flow<Resource<List<DisasterGeometry>>>

}