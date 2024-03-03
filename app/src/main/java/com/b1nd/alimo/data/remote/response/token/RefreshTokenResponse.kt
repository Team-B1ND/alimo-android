package com.b1nd.alimo.data.remote.response.token

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val accessToken: String
)
