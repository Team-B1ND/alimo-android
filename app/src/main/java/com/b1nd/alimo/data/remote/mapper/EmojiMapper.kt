package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.EmojiModel
import com.b1nd.alimo.data.remote.response.detail.EmojiResponse

internal fun EmojiResponse.toModel() =
    EmojiModel(
        count = count,
        emojiName = emojiName
    )


internal fun List<EmojiResponse>.toModels() =
    this.map {
        it.toModel()
    }