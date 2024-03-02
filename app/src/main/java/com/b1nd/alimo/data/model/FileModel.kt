package com.b1nd.alimo.data.model

data class FileModel(
    val fileUrl : String,
    val fileName: String,
    val fileSize: Int,
    val filetype: String?,
    val imageOrFile: String
)