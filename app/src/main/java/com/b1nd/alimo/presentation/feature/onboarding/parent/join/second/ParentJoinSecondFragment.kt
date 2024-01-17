package com.b1nd.alimo.presentation.feature.onboarding.parent.join.second

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentJoinSecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentJoinSecondFragment:
    BaseFragment<FragmentParentJoinSecondBinding, ParentJoinSecondViewModel>(
    R.layout.fragment_parent_join_second
) {
    override val viewModel: ParentJoinSecondViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}