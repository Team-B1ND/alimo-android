package com.b1nd.alimo.data.remote.response.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileCategoryResponse(
    val roles: List<String>
)