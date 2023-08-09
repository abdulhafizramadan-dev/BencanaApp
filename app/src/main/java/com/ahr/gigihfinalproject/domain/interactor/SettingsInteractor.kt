package com.ahr.gigihfinalproject.domain.interactor

import com.ahr.gigihfinalproject.domain.model.UserTheme
import com.ahr.gigihfinalproject.domain.repository.TmaMonitorRepository
import com.ahr.gigihfinalproject.domain.repository.UserPreferenceRepository
import com.ahr.gigihfinalproject.domain.usecase.SettingsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsInteractor @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository,
    private val tmaMonitorRepository: TmaMonitorRepository
) : SettingsUseCase {

    override fun getUserTheme(): Flow<UserTheme> {
        return userPreferenceRepository.getUserTheme()
    }

    override suspend fun updateUserTheme(theme: UserTheme) {
        userPreferenceRepository.updateUserTheme(theme)
    }

    override suspend fun updateUserNotificationTmaMonitoringPreference(state: Boolean) {
        userPreferenceRepository.updateUserNotificationTmaMonitoringPreference(state)
    }

    override fun getUserNotificationTmaMonitoringPreference(): Flow<Boolean> {
        return userPreferenceRepository.getUserNotificationTmaMonitoringPreference()
    }

    override suspend fun runOneTimeTmaMonitor() {
        tmaMonitorRepository.runOneTimeTmaMonitor()
    }

    override suspend fun runPeriodicTmaMonitor() {
        tmaMonitorRepository.runPeriodicTmaMonitor()
    }

    override suspend fun cancelPeriodicTmaMonitor() {
        tmaMonitorRepository.cancelPeriodicTmaMonitor()
    }
}