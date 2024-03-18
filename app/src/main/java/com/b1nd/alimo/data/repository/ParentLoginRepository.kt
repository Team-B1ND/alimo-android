package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.ParentLoginModel
import com.b1nd.alimo.data.remote.mapper.toModel
import com.b1nd.alimo.data.remote.request.ParentLoginRequest
import com.b1nd.alimo.data.remote.safeFlow
import com.b1nd.alimo.data.remote.service.ParentLoginService
import javax.inject.Inject

class ParentLoginRepository @Inject constructor(
    private val parentLoginService: ParentLoginService
) {
    fun login(data: ParentLoginRequest) =
        safeFlow<ParentLoginModel> {
            val response = parentLoginService.login(data)
            emit(Resource.Success(response.data.toModel()))

        }


}