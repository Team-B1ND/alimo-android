package com.b1nd.alimo.data.remote.request.detail

import com.google.gson.annotations.SerializedName


data class DetailCommentRequest(
    @SerializedName("content")
    val content: String,
    @SerializedName("parentId")
    val parentId: Int?,
)