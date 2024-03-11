package com.b1nd.alimo.data.remote.response.onbaording.dodam

import kotlinx.serialization.Serializable

@Serializable
data class DodamResponse(
    val name: String,
    val profileImage: String,
    val location: String
)
