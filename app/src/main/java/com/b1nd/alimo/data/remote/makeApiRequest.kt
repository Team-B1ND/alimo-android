package com.b1nd.alimo.data.remote

import com.b1nd.alimo.data.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal suspend inline fun <reified T> makeApiPostRequest(
    httpClient: HttpClient,
    endpoint: String,
    crossinline block: HttpRequestBuilder.() -> Unit
): Flow<Resource<T>> = flow {
    emit(Resource.Loading())
    try {
        val result = httpClient.post(endpoint, block)
        emit(Resource.Success(result.body()))
    } catch (e: Exception) {
        e.printStackTrace()
        emit(Resource.Error(e))
    }
}

internal suspend inline fun <reified T> makeApiGetRequest(
    httpClient: HttpClient,
    endpoint: String,
    crossinline block: HttpRequestBuilder.() -> Unit
): Flow<Resource<T>> = flow {
    emit(Resource.Loading())
    try {
        val result = httpClient.get(endpoint, block)
        emit(Resource.Success(result.body()))
    } catch (e: Exception) {
        e.printStackTrace()
        emit(Resource.Error(e))
    }
}