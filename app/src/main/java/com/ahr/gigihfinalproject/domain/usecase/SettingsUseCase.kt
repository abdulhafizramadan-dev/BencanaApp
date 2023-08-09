package com.ahr.gigihfinalproject.domain.usecase

import com.ahr.gigihfinalproject.domain.model.UserTheme
import kotlinx.coroutines.flow.Flow

interface SettingsUseCase {

    fun getUserTheme(): Flow<UserTheme>

    suspend fun updateUserTheme(theme: UserTheme)

    fun getUserNotificationTmaMonitoringPreference(): Flow<Boolean>

    suspend fun updateUserNotificationTmaMonitoringPreference(state: Boolean)

    suspend fun runOneTimeTmaMonitor()

    suspend fun runPeriodicTmaMonitor()

    suspend fun cancelPeriodicTmaMonitor()

}