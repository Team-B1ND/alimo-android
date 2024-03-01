package com.b1nd.alimo.data.remote.response.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfoResponse(
    val email: String,
    val grade: Int,
    val room: Int,
    val number: Int,
    val name: String,
    val image: String?,
    val childCode: String?,
    val isOffAlarm: Boolean,
    val memberState: String
)