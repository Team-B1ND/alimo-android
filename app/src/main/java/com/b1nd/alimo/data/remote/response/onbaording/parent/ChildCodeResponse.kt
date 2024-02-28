package com.b1nd.alimo.data.remote.response.onbaording.parent

import kotlinx.serialization.Serializable


@Serializable
data class ChildCodeResponse (
    val isCorrectChildCode: Boolean,
    val memberId: Int
)