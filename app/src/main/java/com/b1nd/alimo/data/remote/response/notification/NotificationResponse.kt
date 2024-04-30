package com.b1nd.alimo.data.remote.response.notification

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


data class NotificationResponse(
    @SerializedName("notificationId")
    val notificationId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("createdAt")
    val createdAt: LocalDateTime,
    @SerializedName("content")
    val content: String,
    @SerializedName("speaker")
    val speaker: Boolean,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isBookMarked")
    val isBookmark: Boolean = false,
    @SerializedName("emoji")
    val emoji: String?,
    @SerializedName("images")
    val images: List<FileResponse>,
    @SerializedName("files")
    val files: List<FileResponse>,
)