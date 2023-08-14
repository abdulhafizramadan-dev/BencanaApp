package com.ahr.gigihfinalproject.data

import android.content.Context
import app.cash.turbine.test
import com.ahr.gigihfinalproject.data.local.GigihFinalProjectDatabase
import com.ahr.gigihfinalproject.data.network.service.PetaBencanaService
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.domain.repository.DisasterRepository
import com.ahr.gigihfinalproject.presentation.util.DataDummy
import com.ahr.gigihfinalproject.presentation.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

private const val DEFAULT_TIME_PERIOD_SECOND = 259200L

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DisasterRepositoryTest {

    @Mock
    private lateinit var petaBencanaService: PetaBencanaService
    @Mock
    private lateinit var gigihFinalProjectDatabase: GigihFinalProjectDatabase

    @Mock
    private lateinit var mockContext: Context

    private lateinit var disasterRepository: DisasterRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        disasterRepository = DisasterRepositoryImpl(petaBencanaService, gigihFinalProjectDatabase, mockContext)
    }

    @Test
    @Ignore
    fun getDisasterReportWithFilter() = runTest {
        val expectedDisasterReportResponse = DataDummy.generateDisasterReportResponse()
        `when`(petaBencanaService.getLatestDisasterInformation(DEFAULT_TIME_PERIOD_SECOND)).thenReturn(expectedDisasterReportResponse)
        disasterRepository.getDisasterReportWithFilter(null, null, null).test {
            val loadingEmit = awaitItem()
            assertEquals(loadingEmit, Resource.Loading)
            awaitItem()
        }
        verify(petaBencanaService).getLatestDisasterInformation(DEFAULT_TIME_PERIOD_SECOND)
    }
}