package com.b1nd.alimo.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class DodamRequest(
    val id: String,
    val pw: String,
    val clientId: String,
    val redirectUrl: String,

)
