package com.ahr.gigihfinalproject.data.network.service

import com.ahr.gigihfinalproject.data.network.response.DisasterReportResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PetaBencanaService {

    @GET("reports?timeperiod=172800")
    suspend fun getLatestDisasterInformation(): DisasterReportResponse

    @GET("reports")
    suspend fun getDisasterReport(
        @Query("disaster") disaster: String
    ): DisasterReportResponse

}