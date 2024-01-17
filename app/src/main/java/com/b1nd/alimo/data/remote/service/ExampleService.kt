package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.ApiResult
import com.b1nd.alimo.data.remote.request.ExampleRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow


interface ExampleService {
    suspend fun getExample(data: ExampleRequest): Flow<ApiResult<BaseResponse<ExampleRequest>>>

    suspend fun postExample(data: ExampleRequest): Flow<ApiResult<BaseResponse<ExampleRequest>>>
}