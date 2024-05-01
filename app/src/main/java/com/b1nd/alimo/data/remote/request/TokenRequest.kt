package com.b1nd.alimo.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val refreshToken: String
)
