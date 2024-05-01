package com.b1nd.alimo.data.remote.response.token

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)
