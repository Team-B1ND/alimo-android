package com.b1nd.alimo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.remote.pagesource.HomePagingSource
import com.b1nd.alimo.data.remote.safeFlow
import com.b1nd.alimo.data.remote.service.HomeService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeService: HomeService
) {

    fun getPost(
        category: String
    ): Flow<PagingData<NotificationModel>> =
        Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = { HomePagingSource(homeService, category) }
        ).flow


    suspend fun getCategory(

    ) = homeService.getCategory()


    suspend fun getSpeaker(

    ) = homeService.getSpeaker()

    suspend fun patchEmoji(
        notificationId: Int,
        emoji: String
    ) = safeFlow<String?> {
        homeService.patchEmoji(
            notificationId = notificationId,
            emoji = emoji
        ).apply {
            errorCheck()
        }
        emit(Resource.Success(null))
    }
}