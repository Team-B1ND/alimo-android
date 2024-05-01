package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.remote.request.ParentJoinRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.ChildCodeResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.MemberNameResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.ParentLoginResponse
import com.b1nd.alimo.di.url.AlimoUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject


class ParentJoinService @Inject constructor(
    private val httpClient: HttpClient
){
    suspend fun singUp(data: ParentJoinRequest): BaseResponse<String?> =
        httpClient.post(AlimoUrl.SIGN_UP){
            setBody(data)
        }.body()

    suspend fun emailCheck(email: String, code: String): BaseResponse<ParentLoginResponse> =
        httpClient.get(AlimoUrl.Member.GET_EMAIL){
            parameter("email", email)
            parameter("code", code)
        }.body()

    suspend fun childCode(query: String): BaseResponse<ChildCodeResponse> =
        httpClient.post(AlimoUrl.CHILD_CODE){
            parameter("child-code", query)
        }.body()

    suspend fun member(query: String): BaseResponse<MemberNameResponse> =
        httpClient.get(AlimoUrl.Member.STUDENT_SEARCH){
            parameter("child-code", query)
        }.body()

    suspend fun postEmailsVerification(query: String): BaseResponse<String?> =
        httpClient.post(AlimoUrl.Member.POST_EMAIL){
            parameter("email", query)
        }.body()



}