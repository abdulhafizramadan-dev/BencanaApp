package com.ahr.gigihfinalproject.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.gigihfinalproject.domain.model.UserTheme
import com.ahr.gigihfinalproject.domain.usecase.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase
) : ViewModel() {

    private val _userTheme = MutableStateFlow(UserTheme.Default)
    val userTheme get() = _userTheme.asStateFlow()

    private val _userNotificationTmaMonitoringPreference = MutableStateFlow(false)
    val userNotificationTmaMonitoringPreference get() = _userNotificationTmaMonitoringPreference.asStateFlow()

    fun getUserTheme() {
        viewModelScope.launch {
            settingsUseCase.getUserTheme().collectLatest {
                _userTheme.value = it
            }
        }
    }

    fun getUserNotificationTmaMonitoring() {
        viewModelScope.launch {
            settingsUseCase.getUserNotificationTmaMonitoringPreference().collectLatest {
                _userNotificationTmaMonitoringPreference.value = it
            }
        }
    }

    fun updateUserTheme(theme: UserTheme) {
        viewModelScope.launch {
            settingsUseCase.updateUserTheme(theme = theme)
        }
    }

    fun updateUserNotificationBaseWaterSetting(state: Boolean) {
        viewModelScope.launch {
            settingsUseCase.updateUserNotificationTmaMonitoringPreference(state)
        }
    }

    fun runFirstTmaMonitoring() {
        viewModelScope.launch {
            settingsUseCase.runOneTimeTmaMonitor()
        }
    }

    fun runPeriodicTmaMonitoring() {
        viewModelScope.launch {
            settingsUseCase.runPeriodicTmaMonitor()
        }
    }

    fun cancelNotificationBaseWaterWorker() {
        viewModelScope.launch {
            settingsUseCase.cancelPeriodicTmaMonitor()
        }
    }

}