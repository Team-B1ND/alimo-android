package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.ParentLoginRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.ParentLoginResponse
import kotlinx.coroutines.flow.Flow
interface ParentLoginService {
    suspend fun login(data: ParentLoginRequest): Flow<Resource<BaseResponse<ParentLoginResponse>>>
}