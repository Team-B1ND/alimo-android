package com.b1nd.alimo.data.model

import com.b1nd.alimo.data.local.entity.TokenEntity

data class TokenModel(
    val token: String?,
    val refreshToken: String?
)

internal fun TokenEntity.toModel(): TokenModel {
    return TokenModel(
        token = token,
        refreshToken = refreshToken
    )
}
