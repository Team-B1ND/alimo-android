package com.b1nd.alimo.data.remote.response.detail

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class CommentResponse(
    @SerializedName("commentId")
    val commentId: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("commenterId")
    val commenterId: Int,
    @SerializedName("commentor")
    val commenter: String,
    @SerializedName("createdAt")
    val createdAt: LocalDateTime,
    @SerializedName("profileImage")
    val profileImage: String?,
    @SerializedName("subComments")
    val subComments: List<SubCommentResponse>,
)