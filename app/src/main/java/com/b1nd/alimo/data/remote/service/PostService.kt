package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.remote.request.detail.DetailEmojiRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.notification.NotificationResponse
import com.b1nd.alimo.di.url.AlimoUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
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
        httpClient.get(AlimoUrl.Bookmark.LOAD) {
            parameter("page", page)
            parameter("size", size)
        }.body()

    suspend fun patchEmoji(
        notificationId: Int,
        emoji: String
    ): BaseResponse<String?> =
        httpClient.patch("${AlimoUrl.Emoji.STATUS}/${notificationId}") {
            setBody(
                DetailEmojiRequest(emoji)
            )
        }.body<BaseResponse<String?>>()

    suspend fun pathBookmark(notificationId: Int): BaseResponse<String?> =
        httpClient.post("${AlimoUrl.Bookmark.PATCH}/${notificationId}") {

        }.body<BaseResponse<String?>>()
}