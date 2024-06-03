package com.b1nd.alimo.data.model

import java.time.LocalDateTime

data class CommentModel(
    val commentId: Int,
    val content: String,
    val commenter: String,
    val commenterId: Int,
    val createdAt: LocalDateTime,
    val profileImage: String?,
    val subComments: List<SubCommentModel>,
)