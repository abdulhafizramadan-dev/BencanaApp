package com.ahr.gigihfinalproject.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.domain.model.DisasterFilterTimePeriod
import com.ahr.gigihfinalproject.domain.model.UserTheme
import com.ahr.gigihfinalproject.domain.repository.UserPreferenceRepository
import com.ahr.gigihfinalproject.util.getCurrentTimeSeconds
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferenceRepositoryImpl @Inject constructor(
    private val userPreferences: DataStore<Preferences>,
    @ApplicationContext private val context: Context
) : UserPreferenceRepository {

    private val userTheme = stringPreferencesKey("user_theme")
    private val userNotificationBaseWaterSetting = booleanPreferencesKey("user_notification_base_water_setting")
    private val disasterFilterTimePeriodName = stringPreferencesKey("disaster_filter_timeperiod_name")
    private val disasterFilterTimePeriodSecond = longPreferencesKey("disaster_filter_timeperiod_second")

    override fun getUserTheme(): Flow<UserTheme> {
        return userPreferences.data.map { preferences ->
            val userTheme = preferences[userTheme] ?: UserTheme.Light.name
            UserTheme.valueOf(userTheme)
        }
    }

    override suspend fun updateUserTheme(state: UserTheme) {
        userPreferences.edit { preferences ->
            preferences[userTheme] = state.name
        }
    }

    override fun getUserNotificationTmaMonitoringPreference(): Flow<Boolean> {
        return userPreferences.data.map { preferences ->
            val userNotificationBaseWaterSetting = preferences[userNotificationBaseWaterSetting] ?: false
            userNotificationBaseWaterSetting
        }
    }

    override suspend fun updateUserNotificationTmaMonitoringPreference(state: Boolean) {
        userPreferences.edit { preferences ->
            preferences[userNotificationBaseWaterSetting] = state
        }
    }

    override suspend fun updateDisasterFilterTimePeriod(disasterFilterTimePeriod: DisasterFilterTimePeriod) {
        userPreferences.edit { preferences ->
            preferences[disasterFilterTimePeriodName] = disasterFilterTimePeriod.name
            preferences[disasterFilterTimePeriodSecond] = disasterFilterTimePeriod.timeSecond
        }
    }

    override fun getDisasterFilterTimePeriod(): Flow<DisasterFilterTimePeriod> {
        return userPreferences.data.map { preferences ->
            val todayTimePeriodName = context.getString(R.string.today_disaster_period_name)
            val disasterFilterTimePeriodName = preferences[disasterFilterTimePeriodName] ?: context.getString(R.string.default_disaster_timeperiod_name)
            val disasterFilterTimePeriodSecond = preferences[disasterFilterTimePeriodSecond] ?: context.resources.getInteger(R.integer.default_disaster_timeperiod_second).toLong()
            val finalDisasterFilterTimePeriod = if (disasterFilterTimePeriodName.equals(todayTimePeriodName, true)) {
                getCurrentTimeSeconds()
            } else {
                disasterFilterTimePeriodSecond
            }
            DisasterFilterTimePeriod(name = disasterFilterTimePeriodName, timeSecond = finalDisasterFilterTimePeriod)
        }
    }
}