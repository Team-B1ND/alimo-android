package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Env
import com.b1nd.alimo.data.remote.request.detail.DetailEmojiRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.notification.NotificationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class PostService @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun getBookmark(
        page: Int,
        size: Int
    ): BaseResponse<List<NotificationResponse>> =
        httpClient.get("/bookmark/load") {
            headers {
                header("Authorization", "Bearer ${Env.testToken}")
            }
            parameter("page", page)
            parameter("size", size)
        }.body()

    suspend fun patchEmoji(
        notificationId: Int,
        emoji: String
    ): BaseResponse<String?> =
        httpClient.patch("/emoji/status/${notificationId}") {
            headers {
                header("Authorization", "Bearer ${Env.testToken}")
            }
            setBody(
                DetailEmojiRequest(emoji)
            )
        }.body<BaseResponse<String?>>()

    suspend fun pathBookmark(notificationId: Int): BaseResponse<String?> =
        httpClient.post("/bookmark/patch/${notificationId}") {
            headers {
                header("Authorization", "Bearer ${Env.testToken}")
            }
        }.body<BaseResponse<String?>>()
}