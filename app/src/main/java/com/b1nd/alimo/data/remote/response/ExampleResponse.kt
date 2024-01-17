package com.b1nd.alimo.data.remote.response

import kotlinx.serialization.Serializable


@Serializable
data class ExampleResponse(
    val name: String,
    val title: String
)