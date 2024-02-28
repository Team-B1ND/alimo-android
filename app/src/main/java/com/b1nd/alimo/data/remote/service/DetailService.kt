package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface DetailService {

    suspend fun patchEmojiEdit(
        notificationId: Int,
        reaction: String
    ): Flow<Resource<BaseResponse<String?>>>

}