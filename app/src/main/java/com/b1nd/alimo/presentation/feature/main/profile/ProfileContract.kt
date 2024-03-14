package com.b1nd.alimo.presentation.feature.main.profile


data class ProfileState(
    val loading: Boolean = true,
    val category: List<String> = emptyList(),
    val data: ProfileInfoModel? = null,
    val isAdd: Boolean = false,
)


sealed class ProfileSideEffect {
    data class FailedLoad(val throwable: Throwable): ProfileSideEffect()
    data class FailedWithdrawal(val throwable: Throwable): ProfileSideEffect()
    object Success: ProfileSideEffect()
    object SuccessWithdrawal: ProfileSideEffect()
}