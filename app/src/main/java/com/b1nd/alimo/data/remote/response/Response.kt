package com.b1nd.alimo.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val status: Int,
    val message: String? = null,
)
