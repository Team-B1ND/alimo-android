package com.b1nd.alimo.presentation.feature.onboarding.parent.join.third

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentJoinThirdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentJoinThirdFragment:
    BaseFragment<FragmentParentJoinThirdBinding, ParentJoinThirdViewModel>(
    R.layout.fragment_parent_join_third
) {
    override val viewModel: ParentJoinThirdViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}