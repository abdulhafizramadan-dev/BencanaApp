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

    private val _userTheme = MutableStateFlow<UserTheme>(UserTheme.Default)
    val userTheme get() = _userTheme.asStateFlow()

    init {
        viewModelScope.launch {
            settingsUseCase.getUserTheme().collectLatest {
                _userTheme.value = it
            }
        }
    }

    fun updateUserTheme(theme: UserTheme) {
        viewModelScope.launch {
            settingsUseCase.updateUserTheme(theme = theme)
        }
    }

}