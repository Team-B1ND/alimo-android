package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Env.testToken
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.detail.DetailEmojiRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.service.DetailService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class DetailRepository @Inject constructor(
    private val httpClient: HttpClient
): DetailService {

    override suspend fun patchEmojiEdit(
        notificationId: Int,
        reaction: String
    ): Flow<Resource<BaseResponse<String?>>> = flow {
        try {
            val body = httpClient.patch("/emoji/status/${notificationId}") {
                headers {
                    header("Authorization", "Bearer $testToken")
                }
                setBody(
                    DetailEmojiRequest(reaction)
                )
            }.body<BaseResponse<String?>>()
            emit(Resource.Success(body))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }

    }


}