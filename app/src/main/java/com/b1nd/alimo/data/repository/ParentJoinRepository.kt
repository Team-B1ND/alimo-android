package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.makeApiGetRequest
import com.b1nd.alimo.data.remote.makeApiPostRequest
import com.b1nd.alimo.data.remote.request.ParentJoinRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.Response
import com.b1nd.alimo.data.remote.response.onbaording.parent.ChildCodeResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.MemberNameResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.ParentLoginResponse
import com.b1nd.alimo.data.remote.service.ParentJoinService
import com.b1nd.alimo.di.NoTokenHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ParentJoinRepository @Inject constructor(
    @NoTokenHttpClient private val httpClient: HttpClient
): ParentJoinService {
    override suspend fun singUp(data: ParentJoinRequest): Flow<Resource<Response>> =
        makeApiPostRequest(
            httpClient = httpClient,
            endpoint = "/sign-up"
        ){
            setBody(ParentJoinRequest(
                email = data.email,
                password = data.password,
                fcmToken = data.fcmToken,
                childCode = data.childCode,
                memberId = data.memberId
            ))
        }

    override suspend fun emailCheck(email: String, code: String): Flow<Resource<BaseResponse<ParentLoginResponse>>> =
        makeApiGetRequest(
            httpClient = httpClient,
            endpoint = "/member/emails/verifications"
        ){
            parameter("email", email)
            parameter("code", code)
        }

    override suspend fun childCode(query: String): Flow<Resource<BaseResponse<ChildCodeResponse>>> =
        makeApiPostRequest(
            httpClient = httpClient,
            endpoint = "/verify-childCode"
        ){
            parameter("child-code", query)
        }

    override suspend fun member(query: String): Flow<Resource<BaseResponse<MemberNameResponse>>> =
        makeApiGetRequest(
            httpClient = httpClient,
            endpoint = "/member"
        ){
            parameter("childCode", query)
        }

    override suspend fun postEmailsVerification(query: String): Flow<Resource<Response>> =
        makeApiPostRequest(
            httpClient = httpClient,
            endpoint = "/member/emails/verification-requests"
        ){
            parameter("email", query)
        }


}