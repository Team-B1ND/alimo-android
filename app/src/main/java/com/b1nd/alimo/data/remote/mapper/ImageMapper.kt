package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.ImageModel
import com.b1nd.alimo.data.remote.response.Image.ImageResponse

internal fun ImageResponse.toModel() =
    ImageModel(
        member = name,
        memberProfile = memberProfile,
        createdAt = createdAt,
        images = images.toModels()
    )