package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first

data class ParentJoinFirstState(
    val data: ParentJoinFirstModel? = null
)

data class MemberNameModel(
    val name: String? = null
)

sealed class ParentJoinFistSideEffect{
    data class FailedLoad(val throwable: Throwable): ParentJoinFistSideEffect()
    object Success: ParentJoinFistSideEffect()
}