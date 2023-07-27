package com.ahr.gigihfinalproject.domain.usecase

import com.ahr.gigihfinalproject.domain.model.UserTheme
import kotlinx.coroutines.flow.Flow

interface SettingsUseCase {

    suspend fun updateUserTheme(theme: UserTheme)

    fun getUserTheme(): Flow<UserTheme>

}