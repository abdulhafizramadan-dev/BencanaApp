package com.ahr.gigihfinalproject.data.network.service

import com.ahr.gigihfinalproject.data.network.response.DisasterReportResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PetaBencanaService {

    @GET("reports?timeperiod=172800")
    suspend fun getLatestDisasterInformation(): DisasterReportResponse

    @GET("reports?timeperiod=604800")
    suspend fun getDisasterReportFilterByDisaster(
        @Query("disaster") disaster: String
    ): DisasterReportResponse

    @GET("reports?timeperiod=604800")
    suspend fun getDisasterReportFilterByLocation(
        @Query("admin") admin: String,
    ): DisasterReportResponse

    @GET("reports?timeperiod=604800")
    suspend fun getDisasterReportFilterByDisasterAndLocation(
        @Query("admin") admin: String,
        @Query("disaster") disaster: String
    ): DisasterReportResponse

}