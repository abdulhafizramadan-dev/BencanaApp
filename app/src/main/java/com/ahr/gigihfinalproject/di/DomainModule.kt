package com.ahr.gigihfinalproject.di

import com.ahr.gigihfinalproject.data.DisasterRepositoryImpl
import com.ahr.gigihfinalproject.data.TmaMonitorRepositoryImpl
import com.ahr.gigihfinalproject.data.UserPreferenceRepositoryImpl
import com.ahr.gigihfinalproject.domain.interactor.HomeInteractor
import com.ahr.gigihfinalproject.domain.interactor.SettingsInteractor
import com.ahr.gigihfinalproject.domain.repository.DisasterRepository
import com.ahr.gigihfinalproject.domain.repository.TmaMonitorRepository
import com.ahr.gigihfinalproject.domain.repository.UserPreferenceRepository
import com.ahr.gigihfinalproject.domain.usecase.HomeUseCase
import com.ahr.gigihfinalproject.domain.usecase.SettingsUseCase
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@ExperimentalPermissionsApi
@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindDisasterRepository(
        disasterRepositoryImpl: DisasterRepositoryImpl
    ): DisasterRepository

    @Binds
    abstract fun bindHomeUseCase(
        homeInteractor: HomeInteractor
    ): HomeUseCase

    @Binds
    abstract fun bindUserPreferenceRepository(
        userPreferenceRepositoryImpl: UserPreferenceRepositoryImpl
    ): UserPreferenceRepository

    @Binds
    abstract fun bindSettingsUseCase(
        settingsInteractor: SettingsInteractor
    ): SettingsUseCase

    @Binds
    abstract fun bindTmaMonitorRepository(
        tmaMonitorRepositoryImpl: TmaMonitorRepositoryImpl
    ): TmaMonitorRepository

}