package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.DodamModel
import com.b1nd.alimo.data.remote.response.onbaording.dodam.DodamResponse

internal fun DodamResponse.toModel() =
    DodamModel(
        name = name,
        profileImage = profileImage,
        location = location
    )
