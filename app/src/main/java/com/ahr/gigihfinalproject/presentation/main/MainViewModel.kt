package com.ahr.gigihfinalproject.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.Resource
import com.ahr.gigihfinalproject.domain.usecase.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _disasterReports = MutableStateFlow<Resource<List<DisasterGeometry>>>(Resource.Loading)
    val disasterReports get() = _disasterReports.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000L)
//            homeUseCase.getLatestDisasterInformation().collect {
//                _disasterReports.value = it
//            }
        }

    }

}