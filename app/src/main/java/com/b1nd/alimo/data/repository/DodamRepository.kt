package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.makeApiPostRequest
import com.b1nd.alimo.data.remote.request.DodamRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.dodam.DodamResponse
import com.b1nd.alimo.data.remote.service.DodamService
import com.b1nd.alimo.di.DAuthHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DodamRepository @Inject constructor(
    @DAuthHttpClient private val httpClient: HttpClient
): DodamService {
    override suspend fun login(data: DodamRequest): Flow<Resource<BaseResponse<DodamResponse>>> =
        makeApiPostRequest(
            httpClient = httpClient,
            endpoint = "/api/auth/login/"
        ){
            setBody(data)
        }


}