package com.ahr.gigihfinalproject.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ahr.gigihfinalproject.domain.model.UserTheme
import com.ahr.gigihfinalproject.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceRepositoryImpl @Inject constructor(
    private val userPreferences: DataStore<Preferences>
) : UserPreferenceRepository {

    private val userTheme = stringPreferencesKey("user_theme")

    override suspend fun updateUserTheme(state: UserTheme) {
        userPreferences.edit { preferences ->
            preferences[userTheme] = state.name
        }
    }

    override fun getUserTheme(): Flow<UserTheme> {
        return userPreferences.data.map { preferences ->
            val userTheme = preferences[userTheme] ?: UserTheme.Light.name
            UserTheme.valueOf(userTheme)
        }
    }
}