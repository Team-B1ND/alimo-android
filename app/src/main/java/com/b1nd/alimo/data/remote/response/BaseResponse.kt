package com.b1nd.alimo.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T> (
    val status: Int,
    val message: String,
    val data: T
) {
    fun newResponse(data: T) = BaseResponse(
        status = status,
        message = message,
        data = data
    )
}