package com.b1nd.alimo.di

import com.b1nd.alimo.data.remote.pagesource.HomePagingSource
import com.b1nd.alimo.data.remote.service.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PageModule {

    @Singleton
    @Provides
    fun provideHomePagingSource(homeService: HomeService): HomePagingSource =
        HomePagingSource(homeService)
}