package com.ahr.gigihfinalproject.domain.interactor

import com.ahr.gigihfinalproject.domain.model.UserTheme
import com.ahr.gigihfinalproject.domain.repository.UserPreferenceRepository
import com.ahr.gigihfinalproject.domain.usecase.SettingsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsInteractor @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository
) : SettingsUseCase {
    override suspend fun updateUserTheme(theme: UserTheme) {
        userPreferenceRepository.updateUserTheme(theme)
    }

    override fun getUserTheme(): Flow<UserTheme> {
        return userPreferenceRepository.getUserTheme()
    }
}