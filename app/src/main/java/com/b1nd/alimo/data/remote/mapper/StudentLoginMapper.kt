package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.StudentLoginModel
import com.b1nd.alimo.data.remote.response.onbaording.studnet.StudentLoginResponse

internal fun StudentLoginResponse.toModel() =
    StudentLoginModel(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
