package com.b1nd.alimo.presentation.feature.onboarding.parent.join.third

sealed class ParentJoinThirdSideEffect {

    data class FailedLoad(val throwable: Throwable): ParentJoinThirdSideEffect()
    data class FailedPostEmail(val throwable: Throwable):ParentJoinThirdSideEffect()
    data class FailedEmailCheck(val throwable: Throwable): ParentJoinThirdSideEffect()
    data object Success: ParentJoinThirdSideEffect()
}