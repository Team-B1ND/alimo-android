package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.remote.response.notification.NotificationResponse

internal fun NotificationResponse.toModel() =
    NotificationModel(
        notificationId = notificationId,
        title = title,
        memberId = memberId,
        member = name,
        memberProfile = profileImage,
        createdAt = createdAt,
        content = content,
        speaker = speaker,
        image = image,
        isBookmark = isBookmark,
        isNew = false,
        emoji = emoji,
        images = images.toModels(),
        files = files.toModels()
    )


internal fun List<NotificationResponse>.toModels() =
    this.map {
        it.toModel()
    }