package com.b1nd.alimo.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class ParentLoginRequest(
    val email: String,
    val password: String,
    val fcmToken: String
)
