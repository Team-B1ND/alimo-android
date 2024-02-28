package com.b1nd.alimo.presentation.feature.onboarding.parent.pw.first

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentFindPwFirstBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ParentFindPWFirstFragment: BaseFragment<FragmentParentFindPwFirstBinding, ParentFindPWFirstViewModel>(
    R.layout.fragment_parent_find_pw_first
) {
    override val viewModel: ParentFindPWFirstViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}