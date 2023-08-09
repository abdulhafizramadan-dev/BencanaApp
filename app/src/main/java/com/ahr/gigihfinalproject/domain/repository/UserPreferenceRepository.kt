package com.ahr.gigihfinalproject.domain.repository

import com.ahr.gigihfinalproject.domain.model.DisasterFilterTimePeriod
import com.ahr.gigihfinalproject.domain.model.UserTheme
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

    fun getUserTheme(): Flow<UserTheme>

    suspend fun updateUserTheme(state: UserTheme)

    fun getUserNotificationTmaMonitoringPreference(): Flow<Boolean>

    suspend fun updateUserNotificationTmaMonitoringPreference(state: Boolean)

    fun getDisasterFilterTimePeriod(): Flow<DisasterFilterTimePeriod>

    suspend fun updateDisasterFilterTimePeriod(disasterFilterTimePeriod: DisasterFilterTimePeriod)

}