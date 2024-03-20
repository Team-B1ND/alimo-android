package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.MemberNameModel
import com.b1nd.alimo.data.model.ParentLoginModel
import com.b1nd.alimo.data.model.ResponseModel
import com.b1nd.alimo.data.remote.mapper.toModel
import com.b1nd.alimo.data.remote.request.ParentJoinRequest
import com.b1nd.alimo.data.remote.safeFlow
import com.b1nd.alimo.data.remote.service.ParentJoinService
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstModel
import javax.inject.Inject

class ParentJoinRepository @Inject constructor(
    private val parentJoinService: ParentJoinService
){
    suspend fun singUp(data: ParentJoinRequest) =
        safeFlow<ResponseModel> {
            val response = parentJoinService.singUp(data)
            emit(
                Resource.Success(response.data.toModel())
            )
        }


    suspend fun emailCheck(email: String, code: String)=
        safeFlow<ParentLoginModel> {
            val response = parentJoinService.emailCheck(email, code).errorCheck()
            emit(
                Resource.Success(response.data.toModel())
            )
        }

    suspend fun childCode(query: String) =
        safeFlow<ParentJoinFirstModel> {
            val response = parentJoinService.childCode(query).errorCheck()
            emit(
                Resource.Success(response.data.toModel())
            )
        }
    suspend fun member(query: String) =
        safeFlow<MemberNameModel> {
            val response = parentJoinService.member(query).errorCheck()
            emit(
                Resource.Success(response.data.toModel())
            )
        }
    suspend fun postEmailsVerification(query: String) =
        safeFlow<ResponseModel> {
            val response = parentJoinService.postEmailsVerification(query)
            emit(
                Resource.Success(response.data.toModel())
            )
        }



}