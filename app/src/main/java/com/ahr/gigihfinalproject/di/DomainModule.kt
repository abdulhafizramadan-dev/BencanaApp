package com.ahr.gigihfinalproject.di

import com.ahr.gigihfinalproject.data.DisasterRepositoryImpl
import com.ahr.gigihfinalproject.domain.interactor.HomeInteractor
import com.ahr.gigihfinalproject.domain.repository.DisasterRepository
import com.ahr.gigihfinalproject.domain.usecase.HomeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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

}