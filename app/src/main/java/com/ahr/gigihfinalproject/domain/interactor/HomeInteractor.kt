package com.ahr.gigihfinalproject.domain.interactor

import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.domain.repository.DisasterRepository
import com.ahr.gigihfinalproject.domain.usecase.HomeUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeInteractor @Inject constructor(private val disasterRepository: DisasterRepository): HomeUseCase {

    override fun getLatestDisasterInformation(): Flow<Resource<List<DisasterGeometry>>> {
        return disasterRepository.getLatestDisasterInformation()
    }

    override fun getDisasterReport(disasterType: DisasterType): Flow<Resource<List<DisasterGeometry>>> {
        return disasterRepository.getDisasterReport(disasterType)
    }

    override fun getProvinces(query: String): Flow<List<Province>> {
        return disasterRepository.getProvinces(query)
    }

    override fun getDisasterFilter(): Flow<List<DisasterType>> {
        return disasterRepository.getDisasterFilter()
    }
}