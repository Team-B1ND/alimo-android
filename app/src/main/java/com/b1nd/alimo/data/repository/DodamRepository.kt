package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.DodamModel
import com.b1nd.alimo.data.remote.mapper.toModel
import com.b1nd.alimo.data.remote.request.DodamRequest
import com.b1nd.alimo.data.remote.safeFlow
import com.b1nd.alimo.data.remote.service.DodamService
import javax.inject.Inject


class DodamRepository @Inject constructor(
    private val dodamService: DodamService
) {
    fun login(data: DodamRequest) =
        safeFlow<DodamModel> {
            val response = dodamService.login(data)
            emit(
                Resource.Success(
                    response.data.toModel()
                )
            )
        }


}