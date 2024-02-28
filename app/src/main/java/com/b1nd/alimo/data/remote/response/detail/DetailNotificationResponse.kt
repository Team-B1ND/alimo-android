package com.b1nd.alimo.data.remote.response.detail

import com.b1nd.alimo.data.model.NotificationModel
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


data class DetailNotificationResponse(
    @SerializedName("notificationId")
    val notificationId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("name")
    val name: String,
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
    @SerializedName("isBookmark")
    val isBookmark: Boolean = false,
) {
    fun toModel() = NotificationModel(
        notificationId = notificationId,
        title = title,
        member = name,
        memberProfile = profileImage,
        createdAt = createdAt,
        content = content,
        speaker = speaker,
        image = image,
        isBookmark = isBookmark,
        isNew = false
    )
}