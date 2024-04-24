package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.CategoryModel
import com.b1nd.alimo.data.remote.mapper.toModel
import com.b1nd.alimo.data.remote.safeFlow
import com.b1nd.alimo.data.remote.service.ProfileService
import com.b1nd.alimo.presentation.feature.main.profile.ProfileInfoModel
import com.b1nd.alimo.presentation.feature.main.profile.toModel
import javax.inject.Inject


class ProfileRepository @Inject constructor(
    private val service: ProfileService
) {

    suspend fun getInfo() = safeFlow<ProfileInfoModel> {
        val response = service.getInfo().errorCheck()
        emit(
            Resource.Success(response.data.toModel())
        )
    }

    suspend fun getCategory() = safeFlow<CategoryModel> {
        val response = service.getCategory().errorCheck()
        emit(
            Resource.Success(response.data.toModel())
        )
    }

    suspend fun setAlarmState(value: Boolean) = safeFlow<String?> {
        val response = service.setAlarmState(value)
        emit(
            Resource.Success(response.message)
        )
    }
        
        
    suspend fun deleteWithdrawal() = safeFlow<String?> {
        val response = service.deleteWithdrawal()
        emit(
            Resource.Success(null)
        )
    }

    fun deleteToken(){
        service.deleteToken()
    }


}