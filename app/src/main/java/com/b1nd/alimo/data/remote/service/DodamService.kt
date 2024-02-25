package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.DodamRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.dodam.DodamResponse
import kotlinx.coroutines.flow.Flow


interface DodamService {
    suspend fun login(data: DodamRequest): Flow<Resource<BaseResponse<DodamResponse>>>
}