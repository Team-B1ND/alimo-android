package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.remote.response.notification.NotificationResponse

internal fun List<NotificationResponse>.toModels() =
    this.map {
        it.toModel()
    }