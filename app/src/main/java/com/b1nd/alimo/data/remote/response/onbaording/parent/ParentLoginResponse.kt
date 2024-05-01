package com.b1nd.alimo.data.remote.response.onbaording.parent

import kotlinx.serialization.Serializable


@Serializable
data class ParentLoginResponse (
    val accessToken: String,
    val refreshToken: String
)