<<<<<<<< HEAD:app/src/main/java/com/b1nd/alimo/feature/onboarding/first/OnboardingFirstFragment.kt
package com.b1nd.alimo.feature.onboarding.first
========
package com.b1nd.alimo.presentation.feature.onboarding.first
>>>>>>>> develop:app/src/main/java/com/b1nd/alimo/presentation/feature/onboarding/first/OnboardingFirstFragment.kt

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentOnboardingFirstBinding
import dagger.hilt.android.AndroidEntryPoint

<<<<<<<< HEAD:app/src/main/java/com/b1nd/alimo/feature/onboarding/first/OnboardingFirstFragment.kt
class OnboardingFirstFragment:BaseFragment<FragmentOnboardingFirstBinding, com.b1nd.alimo.feature.onboarding.first.OnboardingFirstViewModel>(
========
@AndroidEntryPoint
class OnboardingFirstFragment:
    BaseFragment<FragmentOnboardingFirstBinding, OnboardingFirstViewModel>(
>>>>>>>> develop:app/src/main/java/com/b1nd/alimo/presentation/feature/onboarding/first/OnboardingFirstFragment.kt
    R.layout.fragment_onboarding_first) {

    override val viewModel: com.b1nd.alimo.feature.onboarding.first.OnboardingFirstViewModel by viewModels()

    override fun initView() {

    }
}