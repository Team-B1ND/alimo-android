package com.b1nd.alimo.data.model

import java.time.LocalDateTime


data class NotificationModel(
    val notificationId: Int,
    val title: String,
    val member: String,
    val memberProfile: String?,
    val createdAt: LocalDateTime,
    val content: String,
    val speaker: Boolean,
    val image: String?,
    val isBookmark: Boolean,
    val isNew: Boolean
)

//data class NotificationModel(
//    val notificationId: Int,
//    val title: String,
//    val content: String,
//    val speaker: Boolean,
//    val createdAt: LocalDateTime,
//    val member: String
//) {
//    fun toTest() = PostItem(
//        id = notificationId,
//        author = member,
//        authorProfile = "https://i.namu.wiki/i/oWwzGY2QClH5wDvwfZ5XEKVDHzeCj9iK07xicokv1huyYoLtheLWjCJIundAFcC6AjIi2zNgpQ4IFtPNvnb5IXC92lAyS7dx-wC6P1DImBr5KfqzoPpWZZAE_UY8RqV6yx0XUrL90gt7m63EA3-H_A.webp",
//        createAt = createdAt,
//        content = content,
//        image = null,
//        isBookmark = Random.nextBoolean(),
//        isNew = true
//    )
//}
//
//fun List<NotificationModel>.toTests() =
//    this.map {
//        it.toTest()
//    }