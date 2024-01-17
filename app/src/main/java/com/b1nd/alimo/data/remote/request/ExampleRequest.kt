package com.b1nd.alimo.data.remote.request

import kotlinx.serialization.Serializable


@Serializable
data class ExampleRequest(
    val id: Int,
    val name: String
)