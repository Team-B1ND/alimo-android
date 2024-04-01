package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.Response
import com.b1nd.alimo.data.remote.response.profile.ProfileCategoryResponse
import com.b1nd.alimo.data.remote.response.profile.ProfileInfoResponse
import com.b1nd.alimo.di.url.AlimoUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import javax.inject.Inject

class ProfileService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getInfo(): BaseResponse<ProfileInfoResponse> =
        httpClient.get(AlimoUrl.Member.INFO) {

        }.body()

    suspend fun getCategory(): BaseResponse<ProfileCategoryResponse> =
        httpClient.get(AlimoUrl.Category.MEMBER_LIST) {

        }.body()

    suspend fun setAlarmState(value: Boolean): Response =
        httpClient.post(AlimoUrl.Member.ALARM) {
            parameter("status", value)
        }.body()

    suspend fun deleteWithdrawal(): BaseResponse<String?> =
        httpClient.delete(AlimoUrl.Member.DELETE) {

        }.body()
}