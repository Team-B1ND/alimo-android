package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.makeApiPostRequest
import com.b1nd.alimo.data.remote.request.StudentLoginRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.studnet.StudentLoginResponse
import com.b1nd.alimo.data.remote.service.StudentLoginService
import com.b1nd.alimo.di.NoTokenHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StudentLoinRepository @Inject constructor(
    @NoTokenHttpClient private val httpClient: HttpClient
): StudentLoginService {
    override suspend fun login(data: StudentLoginRequest): Flow<Resource<BaseResponse<StudentLoginResponse>>> =
        makeApiPostRequest(
            httpClient = httpClient,
            endpoint = "/sign-in/dodam"
        ){
            setBody(
                data
            )
        }
}