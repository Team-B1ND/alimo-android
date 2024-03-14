package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.CategoryModel
import com.b1nd.alimo.data.remote.response.home.HomeCategoryResponse

internal fun HomeCategoryResponse.toModel() =
    CategoryModel(
        roles = roles
    )


internal fun List<HomeCategoryResponse>.toModels() =
    this.map {
        it.toModel()
    }