package com.b1nd.alimo.di

import com.b1nd.alimo.data.remote.pagesource.HomePagingSource
import com.b1nd.alimo.data.remote.service.HomeServices
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
    fun provideHomePagingSource(homeServices: HomeServices): HomePagingSource =
        HomePagingSource(homeServices)
}