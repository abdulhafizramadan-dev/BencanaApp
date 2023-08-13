package com.ahr.gigihfinalproject.domain.interactor

import com.ahr.gigihfinalproject.domain.model.DisasterFilterTimePeriod
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.domain.repository.DisasterRepository
import com.ahr.gigihfinalproject.domain.repository.UserPreferenceRepository
import com.ahr.gigihfinalproject.domain.usecase.HomeUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeInteractor @Inject constructor(
    private val disasterRepository: DisasterRepository,
    private val userPreferenceRepository: UserPreferenceRepository
): HomeUseCase {

    override fun getDisasterReportWithFilter(
        timePeriod: DisasterFilterTimePeriod?,
        province: Province?,
        disasterType: DisasterType?
    ): Flow<Resource<List<DisasterGeometry>>> {
        return disasterRepository.getDisasterReportWithFilter(timePeriod = timePeriod, province = province, disasterType = disasterType)
    }

    override fun getProvinces(query: String): Flow<List<Province>> {
        return disasterRepository.getProvinces(query)
    }

    override fun getDisasterFilter(): Flow<List<DisasterType>> {
        return disasterRepository.getDisasterFilter()
    }

    override fun getDisasterTimePeriodFilter(selectedDisasterTimePeriod: DisasterFilterTimePeriod?): Flow<List<DisasterFilterTimePeriod>> {
        return disasterRepository.getDisasterTimePeriodFilter(selectedDisasterTimePeriod = selectedDisasterTimePeriod)
    }

    override fun getDisasterFilterTimePeriodPreference(): Flow<DisasterFilterTimePeriod?> {
        return userPreferenceRepository.getDisasterFilterTimePeriod()
    }

    override suspend fun updateDisasterFilterTimePeriodPreference(disasterFilterTimePeriod: DisasterFilterTimePeriod) {
        userPreferenceRepository.updateDisasterFilterTimePeriod(disasterFilterTimePeriod)
    }
}