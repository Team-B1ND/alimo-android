package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.StudentLoginRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.studnet.StudentLoginResponse
import kotlinx.coroutines.flow.Flow
interface StudentLoginService {

    suspend fun login(data: StudentLoginRequest): Flow<Resource<BaseResponse<StudentLoginResponse>>>
}