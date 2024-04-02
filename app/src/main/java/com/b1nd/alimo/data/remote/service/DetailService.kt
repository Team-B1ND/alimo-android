package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.remote.request.detail.DetailCommentRequest
import com.b1nd.alimo.data.remote.request.detail.DetailEmojiRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.detail.DetailNotificationResponse
import com.b1nd.alimo.data.remote.response.detail.EmojiResponse
import com.b1nd.alimo.di.url.AlimoUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class DetailService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun patchEmojiEdit(
        notificationId: Int,
        reaction: String
    ): BaseResponse<String?> =
        httpClient.patch("${AlimoUrl.Emoji.STATUS}/${notificationId}") {
            setBody(
                DetailEmojiRequest(reaction)
            )
        }.body<BaseResponse<String?>>()

    suspend fun loadNotification(
        notificationId: Int
    ): BaseResponse<DetailNotificationResponse> =
        httpClient.get("${AlimoUrl.Notification.READ}/${notificationId}") {

        }.body<BaseResponse<DetailNotificationResponse>>()

    suspend fun pathBookmark(
        notificationId: Int
    ): BaseResponse<String?> =
        httpClient.post("${AlimoUrl.Bookmark.PATCH}/${notificationId}") {

        }.body<BaseResponse<String?>>()

    suspend fun loadEmoji(
        notificationId: Int
    ): BaseResponse<List<EmojiResponse>> =
        httpClient.get("${AlimoUrl.Emoji.LOAD}/${notificationId}") {

        }.body<BaseResponse<List<EmojiResponse>>>()

    suspend fun postComment(
        notificationId: Int,
        text: String,
        commentId: Int?
    ): BaseResponse<String?> =
        httpClient.post("${AlimoUrl.Comment.CREATE}/${notificationId}") {

            setBody(
                DetailCommentRequest(
                    content = text,
                    parentId = commentId
                )
            )
        }.body<BaseResponse<String?>>()

}