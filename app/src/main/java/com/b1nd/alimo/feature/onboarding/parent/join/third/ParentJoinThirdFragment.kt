package com.b1nd.alimo.feature.onboarding.parent.join.third

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentJoinThirdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentJoinThirdFragment:BaseFragment<FragmentParentJoinThirdBinding, com.b1nd.alimo.feature.onboarding.parent.join.third.ParentJoinThirdViewModel>(
    R.layout.fragment_parent_join_third
) {
    override val viewModel: com.b1nd.alimo.feature.onboarding.parent.join.third.ParentJoinThirdViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}