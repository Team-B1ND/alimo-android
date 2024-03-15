package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.remote.response.BaseResponse
import com.b1nd.alimo.data.remote.response.Image.ImageResponse
import com.b1nd.alimo.di.AppHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class ImageService @Inject constructor(
    @AppHttpClient private val httpClient: HttpClient
) {

    suspend fun getNotificationImage(
        notificationId: Int
    ): BaseResponse<ImageResponse> = httpClient.get("/image/get") {
        parameter("notificationId", notificationId)
    }.body()

//    private fun dummyNotificationImage() =
//        BaseResponse(
//            status = 200,
//            message = "",
//            data = ImageResponse(
//                0,
//                0,
//                "test",
//                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvc9_EjDkhzN51pg9u9N6X56tWgB5BCfxRZ3oE-_jwzw&s",
//                LocalDateTime.now(),
//                listOf(
//                    FileResponse(
//                        "https://i.namu.wiki/i/R0AhIJhNi8fkU2Al72pglkrT8QenAaCJd1as-d_iY6MC8nub1iI5VzIqzJlLa-1uzZm--TkB-KHFiT-P-t7bEg.webp",
//                        "iuImage.png",
//                        0,
//                        "",
//                        ""
//                    ),
//                    FileResponse(
//                        "https://www.nme.com/wp-content/uploads/2021/02/iu-lilac-photo-edam-entertainment@2000x1270.jpg",
//                        "iuImage.jpg",
//                        0,
//                        "",
//                        ""
//                    ),
//                )
//            )
//        )
}