package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.local.dao.ExampleDao
import com.b1nd.alimo.data.model.ExampleModel
import com.b1nd.alimo.data.remote.makeApiGetRequest
import com.b1nd.alimo.data.remote.makeApiPostRequest
import com.b1nd.alimo.data.remote.request.ExampleRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.service.ExampleService
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ExampleRepository @Inject constructor(
    private val httpClient: HttpClient,
    private val exampleDao: ExampleDao
): ExampleService {
    override suspend fun getExample(data: ExampleRequest): Flow<Resource<BaseResponse<ExampleRequest>>> =
        makeApiGetRequest(
            httpClient = httpClient,
            endpoint = "/v1/example/get"
        ) {
            parameter("id", data.id)
            parameter("name", data.name)
        }

    override suspend fun postExample(data: ExampleRequest): Flow<Resource<BaseResponse<ExampleRequest>>> =
        makeApiPostRequest(
            httpClient = httpClient,
            endpoint = "/v1/example/post"
        ) {
            setBody(data)
        }

    suspend fun getLocalData(): Flow<Resource<ExampleModel>> = flow {
        emit(Resource.Loading())
        try {
            exampleDao.getThat().let {
                emit(Resource.Success(it.toModel()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e))
        }
    }
}