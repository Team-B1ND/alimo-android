package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.Image.ImageResponse
import com.b1nd.alimo.di.url.AlimoUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class ImageService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getNotificationImage(
        notificationId: Int
    ): BaseResponse<ImageResponse> = httpClient.get(AlimoUrl.IMAGE) {
        parameter("notificationId", notificationId)
    }.body()

}