package com.b1nd.alimo.data.model

import java.time.LocalDateTime

data class ImageModel(
    val member: String,
    val memberProfile: String?,
    val createdAt: LocalDateTime,
    val images: List<FileModel>,
)