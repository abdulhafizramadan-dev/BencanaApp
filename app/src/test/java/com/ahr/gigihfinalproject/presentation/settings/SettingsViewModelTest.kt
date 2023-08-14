package com.ahr.gigihfinalproject.presentation.settings

import app.cash.turbine.test
import com.ahr.gigihfinalproject.domain.model.UserTheme
import com.ahr.gigihfinalproject.domain.usecase.SettingsUseCase
import com.ahr.gigihfinalproject.presentation.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SettingsViewModelTest {

    @Mock
    private lateinit var settingsUseCase: SettingsUseCase
    private lateinit var settingsViewModel: SettingsViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        settingsViewModel = SettingsViewModel(settingsUseCase)
    }

    @Test
    fun getUserTheme() = runTest {
        val expectedUserTheme = UserTheme.Light
        `when`(settingsUseCase.getUserTheme()).thenReturn(flowOf(expectedUserTheme))
        settingsViewModel.getUserTheme()
        settingsViewModel.userTheme.test {
            val actualUserTheme = awaitItem()
            assertEquals(expectedUserTheme, actualUserTheme)
        }
        verify(settingsUseCase).getUserTheme()
    }

    @Test
    fun getUserNotificationTmaMonitoring() = runTest {
        val expectedUserNotificationTmaMonitoring = true
        `when`(settingsUseCase.getUserNotificationTmaMonitoringPreference()).thenReturn(flowOf(expectedUserNotificationTmaMonitoring))
        settingsViewModel.getUserNotificationTmaMonitoring()
        settingsViewModel.userNotificationTmaMonitoringPreference.test {
            val actualUserNotificationTmaMonitoring = awaitItem()
            assertEquals(expectedUserNotificationTmaMonitoring, actualUserNotificationTmaMonitoring)
        }
        verify(settingsUseCase).getUserNotificationTmaMonitoringPreference()
    }

    @Test
    fun updateUserTheme() = runTest {
        val expectedUserTheme = UserTheme.Dark
        settingsViewModel.updateUserTheme(expectedUserTheme)
        verify(settingsUseCase).updateUserTheme(expectedUserTheme)
    }

    @Test
    fun updateUserNotificationBaseWaterSetting() = runTest {
        val expectedUserNotificationBaseWaterSetting = false
        settingsViewModel.updateUserNotificationBaseWaterSetting(expectedUserNotificationBaseWaterSetting)
        verify(settingsUseCase).updateUserNotificationTmaMonitoringPreference(expectedUserNotificationBaseWaterSetting)
    }

    @Test
    fun runFirstTmaMonitoring() = runTest {
        settingsViewModel.runFirstTmaMonitoring()
        verify(settingsUseCase).runOneTimeTmaMonitor()
    }

    @Test
    fun runPeriodicTmaMonitoring() = runTest {
        settingsViewModel.runPeriodicTmaMonitoring()
        verify(settingsUseCase).runPeriodicTmaMonitor()
    }

    @Test
    fun cancelNotificationBaseWaterWorker() = runTest {
        settingsViewModel.cancelNotificationBaseWaterWorker()
        verify(settingsUseCase).cancelPeriodicTmaMonitor()
    }

}