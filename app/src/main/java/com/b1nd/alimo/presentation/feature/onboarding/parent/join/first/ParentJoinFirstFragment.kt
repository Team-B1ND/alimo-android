package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentJoinFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentJoinFirstFragment:
    BaseFragment<FragmentParentJoinFirstBinding, ParentJoinFirstViewModel>(
    R.layout.fragment_parent_join_first
) {
    override val viewModel: ParentJoinFirstViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}