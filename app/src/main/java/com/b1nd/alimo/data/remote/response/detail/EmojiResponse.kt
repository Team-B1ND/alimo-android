package com.b1nd.alimo.data.remote.response.detail

import com.google.gson.annotations.SerializedName

data class EmojiResponse(
    @SerializedName("emojiName")
    val emojiName: String,
    @SerializedName("count")
    val count: Int
)