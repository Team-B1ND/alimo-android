package com.b1nd.alimo.di

import com.b1nd.alimo.data.remote.service.HomeService
import com.b1nd.alimo.data.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideHomeRepository(homeRepository: HomeRepository): HomeService
}