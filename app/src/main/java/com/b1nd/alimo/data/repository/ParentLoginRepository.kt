package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.makeApiPostRequest
import com.b1nd.alimo.data.remote.request.ParentLoginRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.ParentLoginResponse
import com.b1nd.alimo.data.remote.service.ParentLoginService
import com.b1nd.alimo.di.AppHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParentLoginRepository @Inject constructor(
    @AppHttpClient private val httpClient: HttpClient,
):ParentLoginService {
    override suspend fun login(data: ParentLoginRequest): Flow<Resource<BaseResponse<ParentLoginResponse>>> =
        makeApiPostRequest(
            httpClient = httpClient,
            endpoint = "/sign-in"
        ){
            setBody(data)
        }


}