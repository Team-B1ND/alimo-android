package com.b1nd.alimo.presentation.feature.onboarding.parent.join.second

sealed class ParentJoinSecondSideEffect {
    data class FailedLoad(val throwable: Throwable): ParentJoinSecondSideEffect()
    data class FailedMemberName(val throwable: Throwable): ParentJoinSecondSideEffect()

    data class FailedSignup(val throwable: Throwable): ParentJoinSecondSideEffect()
    data object SuccessSignup: ParentJoinSecondSideEffect()
}