package com.b1nd.alimo.data.remote.response.home

import com.b1nd.alimo.data.model.SpeakerModel
import com.google.gson.annotations.SerializedName


data class HomeSpeakerResponse(
    @SerializedName("notificationId")
    val notificationId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("name")
    val name: String
) {
    fun toModel() = SpeakerModel(
        notificationId = notificationId,
        title = title,
        member = name
    )
}