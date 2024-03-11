package com.b1nd.alimo.data.remote.response.Image

import com.b1nd.alimo.data.remote.response.notification.FileResponse
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ImageResponse(
    @SerializedName("notificationId")
    val notificationId: Int,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImage")
    val memberProfile: String?,
    @SerializedName("createdAt")
    val createdAt: LocalDateTime,
    @SerializedName("images")
    val images: List<FileResponse>,
)