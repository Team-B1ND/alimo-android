package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.ParentJoinRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.Response
import com.b1nd.alimo.data.remote.response.onbaording.parent.ChildCodeResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.MemberNameResponse
import com.b1nd.alimo.data.remote.response.onbaording.parent.ParentLoginResponse
import kotlinx.coroutines.flow.Flow

interface ParentJoinService {
    suspend fun singUp(data: ParentJoinRequest): Flow<Resource<Response>>

    suspend fun emailCheck(email: String, code: String): Flow<Resource<BaseResponse<ParentLoginResponse>>>


    suspend fun childCode(query: String): Flow<Resource<BaseResponse<ChildCodeResponse>>>

    suspend fun member(query: String): Flow<Resource<BaseResponse<MemberNameResponse>>>

    suspend fun postEmailsVerification(query: String): Flow<Resource<Response>>
}