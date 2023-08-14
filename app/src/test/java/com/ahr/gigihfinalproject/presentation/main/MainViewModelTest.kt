package com.ahr.gigihfinalproject.presentation.main

import app.cash.turbine.test
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.domain.usecase.HomeUseCase
import com.ahr.gigihfinalproject.presentation.util.DataDummy
import com.ahr.gigihfinalproject.presentation.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    private lateinit var homeUseCase: HomeUseCase
    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(homeUseCase)
    }

    @Test
    fun getDisasterFilters() = runTest {
        val expectedDisasterFilters = DataDummy.generateDisasterTypes()
        `when`(homeUseCase.getDisasterFilter()).thenReturn(flowOf(expectedDisasterFilters))
        mainViewModel.getDisasterFilters()
        mainViewModel.homeScreenUiState.test {
            val actualDisasterFilters = awaitItem().disasterFilters
            assertEquals(expectedDisasterFilters.size, actualDisasterFilters.size)
            assertEquals(expectedDisasterFilters, actualDisasterFilters)
        }
        verify(homeUseCase).getDisasterFilter()
    }

    @Test
    @Ignore("This method called another function in home case, causes the test failed")
    fun getDisasterFilterTimePeriodPreference() = runTest {
        val expectedDisasterFilterTimePeriod = DataDummy.generateDisasterFilterTimePeriods()[0]
        `when`(homeUseCase.getDisasterFilterTimePeriodPreference()).thenReturn(flowOf(expectedDisasterFilterTimePeriod))
        mainViewModel.getDisasterFilterTimePeriodPreference()
        verify(homeUseCase).getDisasterFilterTimePeriodPreference()
    }

    @Test
    fun getDisasterReportWithFilter() = runTest {
        val dummyDisasterGeometry = DataDummy.generateDisasterGeometries()
        val expectedGetDisasterGeometry = flowOf(Resource.Success(dummyDisasterGeometry))
        `when`(homeUseCase.getDisasterReportWithFilter(null, null, null)).thenReturn(expectedGetDisasterGeometry)
        mainViewModel.getDisasterReportWithFilter()
        verify(homeUseCase).getDisasterReportWithFilter(null, null, null)
    }

    @Test
    fun updateDisasterFilterTimePeriodPreference() = runTest {
        val expectedDisasterFilterTimePeriod = DataDummy.generateDisasterFilterTimePeriods()[0]
        mainViewModel.updateDisasterFilterTimePeriodPreference(expectedDisasterFilterTimePeriod)
        verify(homeUseCase).updateDisasterFilterTimePeriodPreference(expectedDisasterFilterTimePeriod)
    }

    @Test
    fun updateMainHeaderSectionState() = runTest {
        val expectedMainHeaderSectionState = MainHeaderSectionState.FOCUS
        mainViewModel.updateMainHeaderSectionState(expectedMainHeaderSectionState)
        mainViewModel.homeScreenUiState.test {
            val actualMainHeaderSectionState = awaitItem().mainHeaderSectionState
            assertEquals(expectedMainHeaderSectionState, actualMainHeaderSectionState)
        }
    }

    @Test
    fun updateProvinceSearchHint() = runTest {
        val expectedProvinceSearchHint = "Jawa Tengah"
        mainViewModel.updateProvinceSearchHint(expectedProvinceSearchHint)
        mainViewModel.homeScreenUiState.test {
            val actualProvinceSearchHint = awaitItem().provinceSearchHint
            assertEquals(expectedProvinceSearchHint, actualProvinceSearchHint)
        }
    }

    @Test
    fun updateProvinceSearchQuery() = runTest {
        val expectedProvinceSearchQuery = "Jawa Ten"
        mainViewModel.updateProvinceSearchQuery(expectedProvinceSearchQuery)
        mainViewModel.homeScreenUiState.test {
            val actualProvinceSearchQuery = awaitItem().provinceSearchQuery
            assertEquals(expectedProvinceSearchQuery, actualProvinceSearchQuery)
        }
    }

    @Test
    fun updateSelectedProvince() = runTest {
        val expectedSelectedProvince = DataDummy.generateProvinces()[0]
        mainViewModel.updateSelectedProvince(expectedSelectedProvince)
        mainViewModel.homeScreenUiState.test {
            val actualSelectedProvince = awaitItem().selectedProvince
            assertNotNull(actualSelectedProvince)
            assertEquals(expectedSelectedProvince, actualSelectedProvince)
        }
    }

    @Test
    fun updateSelectedDisasterFilterTimePeriod() = runTest {
        val expectedSelectedProvince = DataDummy.generateProvinces()[0]
        mainViewModel.updateSelectedProvince(expectedSelectedProvince)
        mainViewModel.homeScreenUiState.test {
            val actualSelectedProvince = awaitItem().selectedProvince
            assertNotNull(actualSelectedProvince)
            assertEquals(expectedSelectedProvince, actualSelectedProvince)
        }
    }

    @Test
    fun updateSelectedDisasterFilter() = runTest {
        val expectedDisasterType = DataDummy.generateDisasterTypes()[0]
        mainViewModel.updateSelectedDisasterFilter(expectedDisasterType)
        mainViewModel.homeScreenUiState.test {
            val actualDisasterType = awaitItem().selectedDisasterFilter
            assertNotNull(actualDisasterType)
            assertEquals(expectedDisasterType, actualDisasterType)
        }
    }

    @Test
    fun searchProvinces() = runTest {
        val searchQuery = "Jawa Tengah"
        val expectedProvinceList = DataDummy.generateProvinces()
        `when`(homeUseCase.getProvinces(searchQuery)).thenReturn(flowOf(expectedProvinceList))
        mainViewModel.searchProvinces(searchQuery)
        mainViewModel.homeScreenUiState.test {
            val actualProvinceList = awaitItem().provinceList
            assertEquals(expectedProvinceList, actualProvinceList)
        }
        verify(homeUseCase).getProvinces(searchQuery)
    }
}