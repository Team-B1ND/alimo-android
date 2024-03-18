package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.ParentLoginModel
import com.b1nd.alimo.data.remote.response.onbaording.parent.ParentLoginResponse


internal fun ParentLoginResponse.toModel() =
    ParentLoginModel(
        accessToken = accessToken,
        refreshToken = refreshToken
    )