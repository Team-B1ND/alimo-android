package com.b1nd.alimo.di

import LocalDateTimeTypeAdapter
import android.util.Log
import com.b1nd.alimo.BuildConfig
import com.b1nd.alimo.data.local.dao.ExampleDao
import com.b1nd.alimo.data.remote.service.ExampleService
import com.b1nd.alimo.data.remote.service.ProfileService
import com.b1nd.alimo.data.repository.ExampleRepository
import com.b1nd.alimo.data.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson
import io.ktor.serialization.kotlinx.json.json
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideKtorHttpClient() = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson {
                registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
                setPrettyPrinting()
                setLenient()
            }
            json()
        }
        install(DefaultRequest) {
            url(BuildConfig.SERVER_URL)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("ktor_logger:", message)
                }
            }
            level = LogLevel.ALL
        }
        install(ResponseObserver) {
            onResponse { response ->
                Log.d("http_status:", "${response.status.value}")
            }
        }
    }

    @Singleton
    @Provides
    fun provideExampleRepository(httpClient: HttpClient, exampleDao: ExampleDao): ExampleService =
        ExampleRepository(httpClient, exampleDao)


    @Singleton
    @Provides
    fun provideProfileRepository(httpClient: HttpClient): ProfileService =
        ProfileRepository(httpClient)
}