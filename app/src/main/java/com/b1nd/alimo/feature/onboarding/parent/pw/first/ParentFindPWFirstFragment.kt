package com.b1nd.alimo.feature.onboarding.parent.pw.first

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentFindPwFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentFindPWFirstFragment:BaseFragment<FragmentParentFindPwFirstBinding, com.b1nd.alimo.feature.onboarding.parent.pw.first.ParentFindPWFirstViewModel>(
    R.layout.fragment_parent_find_pw_first) {
    override val viewModel: com.b1nd.alimo.feature.onboarding.parent.pw.first.ParentFindPWFirstViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}