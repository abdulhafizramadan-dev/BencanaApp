package com.ahr.gigihfinalproject.domain.repository

import com.ahr.gigihfinalproject.domain.model.UserTheme
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

    suspend fun updateUserTheme(state: UserTheme)

    fun getUserTheme(): Flow<UserTheme>

}