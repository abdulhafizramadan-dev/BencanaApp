package com.ahr.gigihfinalproject.di

import android.content.Context
import com.ahr.gigihfinalproject.R
import com.ahr.gigihfinalproject.data.network.service.PetaBencanaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context, ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePetaBencanaService(
        retrofit: Retrofit,
    ): PetaBencanaService {
        return retrofit.create()
    }

}