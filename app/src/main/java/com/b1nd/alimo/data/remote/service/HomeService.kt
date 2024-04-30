package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.remote.mapper.toModels
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.home.HomeSpeakerResponse
import com.b1nd.alimo.data.remote.response.notification.NotificationResponse
import com.b1nd.alimo.di.url.AlimoUrl
import com.b1nd.alimo.presentation.utiles.Dlog
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class HomeService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getPost(
        page: Int,
        size: Int,
        category: String
    ): Resource<List<NotificationModel>> {
        return try {
            val body = httpClient.get(AlimoUrl.Notification.LOAD) {
                parameter("page", page)
                parameter("size", size)
                parameter("category", if (category == "전체") "null" else category)
            }.body<BaseResponse<List<NotificationResponse>>>()
            Dlog.d("getPost: $body")
            Resource.Success(body.data.toModels())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    suspend fun getSpeaker(): BaseResponse<HomeSpeakerResponse?> =
        httpClient.get(AlimoUrl.Notification.SPEAKER) {

        }.body()
}