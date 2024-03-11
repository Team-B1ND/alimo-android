package com.b1nd.alimo.presentation.feature.profile

import com.b1nd.alimo.data.remote.response.profile.ProfileInfoResponse

data class ProfileInfoModel(
    val email: String,
    val grade: Int,
    val room: Int,
    val number: Int,
    val name: String?,
    val image: String?,
    val childCode: String?,
    val isOffAlarm: Boolean,
    val memberState: String
)

internal fun ProfileInfoResponse.toModel() =
    ProfileInfoModel(
        email = email,
        grade = grade,
        room = room,
        number = number,
        name = name,
        image = image,
        childCode = childCode,
        isOffAlarm = isOffAlarm,
        memberState = memberState
    )