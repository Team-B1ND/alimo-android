package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.ImageModel
import com.b1nd.alimo.data.remote.mapper.toModel
import com.b1nd.alimo.data.remote.safeFlow
import com.b1nd.alimo.data.remote.service.ImageService
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val imageService: ImageService
) {

    suspend fun getNotification(
        notificationId: Int
    ) = safeFlow<ImageModel> {
        val response = imageService.getNotificationImage(notificationId)
        emit(Resource.Success(response.data.toModel()))
    }
}