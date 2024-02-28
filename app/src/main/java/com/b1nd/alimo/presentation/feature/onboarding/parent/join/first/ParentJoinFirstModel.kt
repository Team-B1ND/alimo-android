package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first

import com.b1nd.alimo.data.remote.response.onbaording.parent.ChildCodeResponse

data class ParentJoinFirstModel(
    var isCorrectChildCode: Boolean = false,
    val memberId: Int? = null
)

internal fun ChildCodeResponse.toModel() =
    ParentJoinFirstModel(
        isCorrectChildCode = isCorrectChildCode,
        memberId = memberId
    )