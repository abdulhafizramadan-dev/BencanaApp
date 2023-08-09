package com.ahr.gigihfinalproject.data.network.service

import com.ahr.gigihfinalproject.data.network.response.DisasterReportResponse
import com.ahr.gigihfinalproject.data.network.response.TmaMonitoringResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PetaBencanaService {

    @GET("reports")
    suspend fun getLatestDisasterInformation(
        @Query("timeperiod") timePeriod: Long
    ): DisasterReportResponse

    @GET("reports")
    suspend fun getDisasterReportFilterByDisaster(
        @Query("disaster") disaster: String,
        @Query("timeperiod") timePeriod: Long
    ): DisasterReportResponse

    @GET("reports")
    suspend fun getDisasterReportFilterByLocation(
        @Query("admin") admin: String,
        @Query("timeperiod") timePeriod: Long
    ): DisasterReportResponse

    @GET("reports")
    suspend fun getDisasterReportFilterByDisasterAndLocation(
        @Query("admin") admin: String,
        @Query("disaster") disaster: String,
        @Query("timeperiod") timePeriod: Long
    ): DisasterReportResponse

    @GET("floodgauges")
    suspend fun getTmaMonitoring(): TmaMonitoringResponse

}