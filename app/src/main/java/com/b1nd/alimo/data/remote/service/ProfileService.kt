package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.profile.ProfileCategoryResponse
import com.b1nd.alimo.data.remote.response.profile.ProfileInfoResponse
import kotlinx.coroutines.flow.Flow

interface ProfileService {

    suspend fun getInfo(): Flow<Resource<BaseResponse<ProfileInfoResponse>>>

    suspend fun getCategory(): Flow<Resource<BaseResponse<ProfileCategoryResponse>>>

    suspend fun deleteWithdrawal(): Flow<Resource<BaseResponse<String?>>>
}