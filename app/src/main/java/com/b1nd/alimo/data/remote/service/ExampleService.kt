package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.ExampleRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow


interface ExampleService {
    suspend fun getExample(data: ExampleRequest): Flow<Resource<BaseResponse<ExampleRequest>>>

    suspend fun postExample(data: ExampleRequest): Flow<Resource<BaseResponse<ExampleRequest>>>
}