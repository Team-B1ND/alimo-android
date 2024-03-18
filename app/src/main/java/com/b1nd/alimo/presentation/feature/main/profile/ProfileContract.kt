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
    data class FailedLoadCategory(val throwable: Throwable): ProfileSideEffect()
    data class FailedLoadInfo(val throwable: Throwable): ProfileSideEffect()
    data object Success: ProfileSideEffect()
    data object SuccessWithdrawal: ProfileSideEffect()
    data object SuccessLogout: ProfileSideEffect()
}