package com.ahr.gigihfinalproject.data

import android.content.Context
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.data.mapper.toDomain
import com.ahr.gigihfinalproject.data.network.service.PetaBencanaService
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterType
import com.ahr.gigihfinalproject.domain.model.Province
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.domain.repository.DisasterRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DisasterRepositoryImpl @Inject constructor(
    private val petaBencanaService: PetaBencanaService,
    @ApplicationContext private val context: Context
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

    override fun getDisasterReportWithFilter(
        province: Province?,
        disasterType: DisasterType?
    ): Flow<Resource<List<DisasterGeometry>>> = flow<Resource<List<DisasterGeometry>>> {
        emit(Resource.Loading)
        val disasterReportResult = when {
            province != null && disasterType != null -> petaBencanaService.getDisasterReportFilterByDisasterAndLocation(province.code, disasterType.code)
            province != null -> petaBencanaService.getDisasterReportFilterByLocation(province.code)
            disasterType != null -> petaBencanaService.getDisasterReportFilterByDisaster(disasterType.code)
            else -> petaBencanaService.getLatestDisasterInformation()
        }
        val disasterGeometries = disasterReportResult.disasterResult?.disasterObjects?.disasterOutput?.geometries
            ?.map { disasterGeometriesItem -> disasterGeometriesItem.toDomain() }
        emit(Resource.Success(disasterGeometries ?: emptyList()))
    }.catch {
        emit(Resource.Error(it, emptyList()))
    }

    override fun getProvinces(query: String): Flow<List<Province>> = flow {
        val provinceNames = context.resources.getStringArray(R.array.province_names)
        val provinceCodes = context.resources.getStringArray(R.array.province_codes)
        val provinces = provinceNames.mapIndexed { index, _ ->
            Province(
                name = provinceNames[index],
                code = provinceCodes[index]
            )
        }
        if (query.isNotEmpty()) {
            val filteredProvinces = provinces.filter { it.name.contains(query, true) || it.code.contains(query, true) }
            emit(filteredProvinces)
        } else {
            emit(provinces)
        }
    }

    override fun getDisasterFilter(): Flow<List<DisasterType>> = flow {
        val disasterNames = context.resources.getStringArray(R.array.disaster_names)
        val disasterCodes = context.resources.getStringArray(R.array.disaster_codes)
        val provinces = disasterNames.mapIndexed { index, _ ->
            DisasterType(
                name = disasterNames[index],
                code = disasterCodes[index]
            )
        }
        emit(provinces)
    }
}