package com.b1nd.alimo.di

import LocalDateTimeTypeAdapter
import android.util.Log
import com.b1nd.alimo.BuildConfig
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.TokenRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.token.RefreshTokenResponse
import com.b1nd.alimo.data.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import io.ktor.serialization.kotlinx.json.json
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    @AppHttpClient
    fun provideKtorHttpClient(
        tokenRepository: TokenRepository
    ) = HttpClient(CIO) {
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
        install(Auth){
            bearer {
                loadTokens {
                    val accessToken = tokenRepository.getToken().token?: ""
                    BearerTokens(accessToken, "")
                }
                refreshTokens {
                    val refreshToken = tokenRepository.getToken().refreshToken?: ""

                    val accessToken = client.post("${BuildConfig.SERVER_URL}/refresh"){
                        markAsRefreshTokenRequest()
                        setBody(TokenRequest(refreshToken = refreshToken))
                    }.body<Resource<BaseResponse<RefreshTokenResponse>>>().data?.data?.accessToken?: ""
                    BearerTokens(accessToken, "")
                }
                sendWithoutRequest {request ->
                    when(request.url.toString()){
                        "${BuildConfig.SERVER_URL}/refresh" -> false
                        else -> true
                    }
                }
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = TIME_OUT
            connectTimeoutMillis = TIME_OUT
            socketTimeoutMillis = TIME_OUT
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
    private const val TIME_OUT = 60_000L

    @Singleton
    @Provides
    @DAuthHttpClient
    fun provideKtorDodamHttpClient() = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson {
                registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
                setPrettyPrinting()
                setLenient()
            }
            json()
        }
        install(DefaultRequest) {
            url(BuildConfig.DAUTH_SERVER_URL)
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



}