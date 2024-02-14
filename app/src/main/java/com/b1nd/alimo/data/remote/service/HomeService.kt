package com.b1nd.alimo.data.remote.service

import android.util.Log
import com.b1nd.alimo.data.Env
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.remote.makeApiGetRequest
import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.home.HomeCategoryResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.headers
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

class HomeService @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getNotice(
        page: Int
    ): Resource<BaseResponse<List<NotificationModel>>> {
        Log.d("TAG", "getNotice: $page")
        return Resource.Success(dummyNotice(page))
    }

    suspend fun getCategory(

    ): Flow<Resource<BaseResponse<HomeCategoryResponse>>> =
        makeApiGetRequest(httpClient, "/member/category-list") {
            headers {
                header("Authorization", "Bearer ${Env.testToken}")
            }
        }

    private suspend fun dummyNotice(
        page: Int
    ): BaseResponse<List<NotificationModel>> {
        val nowPage = (15*page)
        val data = mutableListOf<NotificationModel>()
        for (i in nowPage..nowPage+14) {
            data.add(
                NotificationModel(
                    notificationId = i,
                    title = "$i i title",
                    content = "$i content",
                    speaker = Random.nextBoolean(),
                    createdAt = LocalDateTime.now(),
                    member = "$i member"
                )
            )
        }

        return BaseResponse(status = 200, message = "success", data = data)
    }
}