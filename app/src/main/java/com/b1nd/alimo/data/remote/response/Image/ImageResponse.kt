package com.b1nd.alimo.data.remote.response.Image

import com.b1nd.alimo.data.remote.response.notification.FileResponse
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ImageResponse(
    @SerializedName("memberName")
    val name: String,
    @SerializedName("profileUrl")
    val memberProfile: String?,
    @SerializedName("createdAt")
    val createdAt: LocalDateTime,
    @SerializedName("imageList")
    val images: List<FileResponse>,
)