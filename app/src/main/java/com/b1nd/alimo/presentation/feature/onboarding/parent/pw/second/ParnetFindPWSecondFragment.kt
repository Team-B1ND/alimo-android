package com.b1nd.alimo.presentation.feature.onboarding.parent.pw.second

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentFindPwFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParnetFindPWSecondFragment:
    BaseFragment<FragmentParentFindPwFirstBinding, ParentFindPWSecondViewModel>(
    R.layout.fragment_parent_find_pw_second
) {
    override val viewModel: ParentFindPWSecondViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}