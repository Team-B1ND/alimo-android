package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.ResponseModel
import com.b1nd.alimo.data.remote.response.Response

internal fun Response.toModel() =
    ResponseModel(
        status = status,
        message = message
    )