package com.b1nd.alimo.data.remote.request.detail

import com.google.gson.annotations.SerializedName


data class DetailEmojiRequest(
    @SerializedName("reaction")
    val reaction: String
)