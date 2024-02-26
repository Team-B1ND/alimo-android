package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Env.testToken
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.makeApiGetRequest
import com.b1nd.alimo.data.remote.makeApiPostRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.Response
import com.b1nd.alimo.data.remote.response.profile.ProfileCategoryResponse
import com.b1nd.alimo.data.remote.response.profile.ProfileInfoResponse
import com.b1nd.alimo.data.remote.service.ProfileService
import com.b1nd.alimo.di.AppHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ProfileRepository @Inject constructor(
    @AppHttpClient private val httpClient: HttpClient
): ProfileService {

    override suspend fun getInfo(): Flow<Resource<BaseResponse<ProfileInfoResponse>>> =
        makeApiGetRequest(httpClient, "/member/info") {

        }

    override suspend fun getCategory(): Flow<Resource<BaseResponse<ProfileCategoryResponse>>> =
        makeApiGetRequest(httpClient, "/member/category-list") {
            headers {
                header("Authorization", "Bearer $testToken")
            }
        }

    override suspend fun setAlarmState(value: Boolean): Flow<Resource<Response>> =
        makeApiPostRequest(
            httpClient = httpClient,
            endpoint = "/member/alarm-on-off"
        ){

            parameter("is_off_alarm", value)
        }

}