package com.b1nd.alimo.data.remote.response.onbaording.studnet

import kotlinx.serialization.Serializable

@Serializable
data class StudentLoginResponse(
    val accessToken: String,
    val refreshToken: String
)
