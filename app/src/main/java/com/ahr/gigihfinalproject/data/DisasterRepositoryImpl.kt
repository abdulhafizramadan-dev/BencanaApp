package com.ahr.gigihfinalproject.data

import com.ahr.gigihfinalproject.data.mapper.toDomain
import com.ahr.gigihfinalproject.data.network.service.PetaBencanaService
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.domain.repository.DisasterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DisasterRepositoryImpl @Inject constructor(
    private val petaBencanaService: PetaBencanaService
) : DisasterRepository {

    override fun getLatestDisasterInformation(): Flow<Resource<List<DisasterGeometry>>> = flow<Resource<List<DisasterGeometry>>> {
        emit(Resource.Loading)
        val disasterReportResult = petaBencanaService.getLatestDisasterInformation()
        val disasterGeometries = disasterReportResult.disasterResult?.disasterObjects?.disasterOutput?.geometries
            ?.map { disasterGeometriesItem -> disasterGeometriesItem.toDomain() }
        emit(Resource.Success(disasterGeometries ?: emptyList()))
    }.catch {
        emit(Resource.Error(it, emptyList()))
    }

    override fun getDisasterReport(disasterType: DisasterType): Flow<Resource<List<DisasterGeometry>>> = flow<Resource<List<DisasterGeometry>>> {
        emit(Resource.Loading)
        val disasterReportResult = petaBencanaService.getDisasterReport(disasterType.query)
        val disasterGeometries = disasterReportResult.disasterResult?.disasterObjects?.disasterOutput?.geometries
            ?.map { disasterGeometriesItem -> disasterGeometriesItem.toDomain() }
        emit(Resource.Success(disasterGeometries ?: emptyList()))
    }.catch {
        emit(Resource.Error(it, emptyList()))
    }
}