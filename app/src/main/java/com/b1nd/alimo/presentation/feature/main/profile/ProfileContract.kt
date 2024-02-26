package com.b1nd.alimo.presentation.feature.main.profile


data class ProfileState(
    val loading: Boolean = true,
    val category: List<String>? = null,
    val data: ProfileInfoModel? = null,
    val isAdd: Boolean = false
)


sealed class ProfileSideEffect {
    data class FailedLoad(val throwable: Throwable): ProfileSideEffect()
    object Success: ProfileSideEffect()
}