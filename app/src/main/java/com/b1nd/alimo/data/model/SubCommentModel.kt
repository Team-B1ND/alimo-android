package com.b1nd.alimo.data.model

import java.time.LocalDateTime

data class SubCommentModel(
    val commentId: Int,
    val content: String,
    val commenter: String,
    val createdAt: LocalDateTime,
    val profileImage: String?,
)