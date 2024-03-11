package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.remote.response.onbaording.parent.ChildCodeResponse
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstModel

internal fun ChildCodeResponse.toModel() =
    ParentJoinFirstModel(
        isCorrectChildCode = isCorrectChildCode,
        memberId = memberId
    )