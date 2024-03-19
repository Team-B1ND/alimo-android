package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.MemberNameModel
import com.b1nd.alimo.data.remote.response.onbaording.parent.MemberNameResponse

internal fun MemberNameResponse.toModel() =
    MemberNameModel(
        name = name
    )