package com.b1nd.alimo.feature.onboarding.parent.pw.second

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentFindPwFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParnetFindPWSecondFragment:BaseFragment<FragmentParentFindPwFirstBinding, com.b1nd.alimo.feature.onboarding.parent.pw.second.ParentFindPWSecondViewModel>(
    R.layout.fragment_parent_find_pw_second
) {
    override val viewModel: com.b1nd.alimo.feature.onboarding.parent.pw.second.ParentFindPWSecondViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}