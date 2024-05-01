package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.CategoryModel
import com.b1nd.alimo.data.remote.response.profile.ProfileCategoryResponse


internal fun ProfileCategoryResponse.toModel() =
    CategoryModel(
        roles = roles
    )