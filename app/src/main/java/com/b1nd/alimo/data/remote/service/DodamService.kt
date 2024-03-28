package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.remote.request.DodamRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.dodam.DodamResponse
import com.b1nd.alimo.di.DAuthHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class DodamService @Inject constructor(
    @DAuthHttpClient private val httpClient: HttpClient
) {
    suspend fun login(data: DodamRequest): BaseResponse<DodamResponse> =
        httpClient.post("/api/auth/login"){
            setBody(data)
        }.body()
}