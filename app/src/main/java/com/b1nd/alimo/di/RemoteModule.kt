package com.b1nd.alimo.di

import LocalDateTimeTypeAdapter
import android.content.Context
import android.content.Intent
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.TokenRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.token.TokenResponse
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.di.url.AlimoUrl
import com.b1nd.alimo.di.url.DodamUrl
import com.b1nd.alimo.presentation.feature.onboarding.OnboardingActivity
import com.b1nd.alimo.presentation.utiles.AlimoApplication
import com.b1nd.alimo.presentation.utiles.Dlog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
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
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideKtorHttpClient(
        tokenRepository: TokenRepository,
        @ApplicationContext context: Context
    ) = HttpClient(CIO) {
        // 어떻게 받을지 설정
        install(ContentNegotiation) {
            gson {
                registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
                setPrettyPrinting()
                setLenient()
            }
        }
        // 요청 및 응답 로그
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Dlog.v("ktor_logger: $message")
                }
            }
            level = LogLevel.ALL
        }
        // 응답 Status값 로그
        install(ResponseObserver) {
            onResponse { response ->
                Dlog.d("http_status: ${response.status.value}")
            }
        }
        // TokenInterceptor
        install(Auth) {
            bearer {
                // 헤더에 AccessToken
                loadTokens {
                    var accessToken = ""
                    tokenRepository.getToken().collect {
                        when (it) {
                            is Resource.Success -> {
                                accessToken = it.data?.token.toString()
                            }
                            is Resource.Error ->{
                                Dlog.e("중간 에러: ${it.error}")
                            }
                            is Resource.Loading ->{
                                Dlog.d("로딩 아래: $it")
                            }
                        }
                    }
                    Dlog.d(": 1엑세스 $accessToken")
                    BearerTokens(accessToken, "")
                }
                // AccessToken 만료되면 RefreshToken을 사용해서 다시 가져옴
                refreshTokens {
                    coroutineScope {
                        var refreshTokne = ""
                        val task = async {
                            runBlocking(Dispatchers.IO) {
                                tokenRepository.getToken().catch {
                                    Dlog.d("위에: $it")
                                }.collect {
                                    when (it) {
                                        is Resource.Success -> {
                                            refreshTokne = it.data?.refreshToken.toString()
                                        }

                                        is Resource.Error -> {
                                            Dlog.e("중간 에러: ${it.error}")
                                        }

                                        is Resource.Loading -> {
                                            Dlog.d("로딩 아래: $it")
                                        }
                                    }

                                }
                            }

                        }
                        task.await()
                        Dlog.d(": 리플레쉬$refreshTokne")

                        val data = client.post(AlimoUrl.REFRESH) {
                            markAsRefreshTokenRequest()
                            setBody(TokenRequest(refreshToken = refreshTokne))
                        }.body<BaseResponse<TokenResponse>>()
                        // 만약 Status가 401 이면 RefreshToken 만료
                        if (data.status == 401) {
                            // intent to onboarding
                            // 현재 context에서 OnboardingActivity로 이동

                            tokenRepository.insert("만료", "")
                            val intent = Intent(
                                context,
                                OnboardingActivity::class.java
                            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.putExtra("data", "만료")
                            val nowActivity = (context as AlimoApplication).nowActivity
                            if (nowActivity.second == "MainActivity") {
                                nowActivity.first?.finish()
                            }
                            context.startActivity(
                                intent
                            )
                        }
                        val accessToken = data.data.accessToken ?: ""
                        Dlog.d("ddfs: $data")
                        BearerTokens(accessToken, "")
                    }
                }
                sendWithoutRequest { request ->

                    when (request.url.toString()) {
                        AlimoUrl.REFRESH -> false
                        AlimoUrl.SignIn.DODAM_SIGN_IN -> false
                        AlimoUrl.SIGN_IN -> false
                        AlimoUrl.SIGN_UP -> false
                        DodamUrl.LOGIN -> false

                        else -> {
                            when(request.url.toString().split("?")[0]){
                                AlimoUrl.CHILD_CODE -> false
                                AlimoUrl.Member.GET_EMAIL -> false
                                AlimoUrl.Member.STUDENT_SEARCH -> false
                                AlimoUrl.Member.POST_EMAIL -> false

                                else -> true
                            }
                        }
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


}