package com.b1nd.alimo.data.remote

import com.b1nd.alimo.data.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal inline fun <reified T> makeApiPostRequest(
    httpClient: HttpClient,
    endpoint: String,
    crossinline block: HttpRequestBuilder.() -> Unit
): Flow<ApiResult<T>> = flow {
    emit(ApiResult.Loading())
    try {
        val result = httpClient.post(endpoint, block)
        emit(ApiResult.Success(result.body()))
    } catch (e: Exception) {
        e.printStackTrace()
        emit(ApiResult.Error(e))
    }
}

internal inline fun <reified T> makeApiGetRequest(
    httpClient: HttpClient,
    endpoint: String,
    crossinline block: HttpRequestBuilder.() -> Unit
): Flow<ApiResult<T>> = flow {
    emit(ApiResult.Loading())
    try {
        val result = httpClient.get(endpoint, block)
        emit(ApiResult.Success(result.body()))
    } catch (e: Exception) {
        e.printStackTrace()
        emit(ApiResult.Error(e))
    }
}