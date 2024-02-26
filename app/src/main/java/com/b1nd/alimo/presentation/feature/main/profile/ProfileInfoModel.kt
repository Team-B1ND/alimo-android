package com.b1nd.alimo.presentation.feature.main.profile

import com.b1nd.alimo.data.remote.response.profile.ProfileInfoResponse

data class ProfileInfoModel(
    val email: String,
    val grade: Int,
    val name: String?,
    val image: String?,
    val childCode: String?,
)

internal fun ProfileInfoResponse.toModel() =
    ProfileInfoModel(
        email = email,
        grade = grade,
        name = name,
        image = image,
        childCode = childCode
    )