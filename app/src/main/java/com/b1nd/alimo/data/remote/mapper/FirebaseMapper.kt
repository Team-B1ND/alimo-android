package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.local.entity.FirebaseTokenEntity
import com.b1nd.alimo.data.model.FirebaseTokenModel


internal fun FirebaseTokenEntity.toModel() =
        FirebaseTokenModel(
            fcmToken = fcmToken
        )