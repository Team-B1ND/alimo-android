package com.b1nd.alimo.data.remote.response.home

import kotlinx.serialization.Serializable

@Serializable
data class HomeCategoryResponse(
    val roles: List<String>
)