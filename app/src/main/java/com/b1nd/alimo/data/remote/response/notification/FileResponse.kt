package com.b1nd.alimo.data.remote.response.notification

import com.google.gson.annotations.SerializedName

data class FileResponse(
    @SerializedName("fileUrl")
    val fileUrl : String,
    @SerializedName("fileName")
    val fileName: String,
    @SerializedName("fileSize")
    val fileSize: Int,
    @SerializedName("filetype")
    val filetype: String,
    @SerializedName("imageOrFile")
    val imageOrFile: String
)