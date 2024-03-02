package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.FileModel
import com.b1nd.alimo.data.remote.response.notification.FileResponse

internal fun FileResponse.toModel() =
    FileModel(
        fileUrl = fileUrl,
        fileName = fileName,
        fileSize = fileSize,
        filetype = filetype,
        imageOrFile = imageOrFile
    )

internal fun List<FileResponse>.toModels() =
    this.map {
        it.toModel()
    }