package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.ApiResult
import com.b1nd.alimo.data.remote.makeApiGetRequest
import com.b1nd.alimo.data.remote.makeApiPostRequest
import com.b1nd.alimo.data.remote.request.ExampleRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.service.ExampleService
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExampleRepository @Inject constructor(
    private val httpClient: HttpClient
): ExampleService {
    override suspend fun getExample(data: ExampleRequest): Flow<ApiResult<BaseResponse<ExampleRequest>>> =
        makeApiGetRequest(
            httpClient = httpClient,
            endpoint = "/v1/example/get"
        ) {
            parameter("id", data.id)
            parameter("name", data.name)
        }

    override suspend fun postExample(data: ExampleRequest): Flow<ApiResult<BaseResponse<ExampleRequest>>> =
        makeApiPostRequest(
            httpClient = httpClient,
            endpoint = "/v1/example/post"
        ) {
            setBody(data)
        }
}