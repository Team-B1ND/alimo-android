package com.b1nd.alimo.di

import com.b1nd.alimo.data.remote.service.ExampleService
import com.b1nd.alimo.data.remote.service.FirebaseTokenService
import com.b1nd.alimo.data.remote.service.TokenService
import com.b1nd.alimo.data.repository.ExampleRepository
import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.b1nd.alimo.data.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// 오류 때문에 Repository를 따로 만듬
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun provideExampleRepository(repository: ExampleRepository): ExampleService


    @Singleton
    @Binds
    fun provideFirebaseTokenRepository(repository: FirebaseTokenRepository): FirebaseTokenService

    @Singleton
    @Binds
    fun provideTokenRepository(repository: TokenRepository): TokenService





}