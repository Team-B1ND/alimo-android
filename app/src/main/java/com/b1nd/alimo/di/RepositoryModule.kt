package com.b1nd.alimo.di

import com.b1nd.alimo.data.remote.service.DodamService
import com.b1nd.alimo.data.remote.service.ExampleService
import com.b1nd.alimo.data.remote.service.FirebaseTokenService
import com.b1nd.alimo.data.remote.service.ParentJoinService
import com.b1nd.alimo.data.remote.service.ParentLoginService
import com.b1nd.alimo.data.remote.service.ProfileService
import com.b1nd.alimo.data.remote.service.StudentLoginService
import com.b1nd.alimo.data.remote.service.TokenService
import com.b1nd.alimo.data.repository.DodamRepository
import com.b1nd.alimo.data.repository.ExampleRepository
import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.b1nd.alimo.data.repository.ParentJoinRepository
import com.b1nd.alimo.data.repository.ParentLoginRepository
import com.b1nd.alimo.data.repository.ProfileRepository
import com.b1nd.alimo.data.repository.StudentLoinRepository
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


    @Singleton
    @Binds
    fun provideProfileRepository(repository: ProfileRepository): ProfileService


    @Singleton
    @Binds
    fun provideParentJoinRepository(repository: ParentJoinRepository): ParentJoinService

    @Singleton
    @Binds
    fun provideStudentLoginRepository(repository: StudentLoinRepository): StudentLoginService

    @Singleton
    @Binds
    fun provideDodamRepository(repository: DodamRepository): DodamService

    @Singleton
    @Binds
    fun provideParentLoginRepository(repository: ParentLoginRepository): ParentLoginService

}