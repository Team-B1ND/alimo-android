package com.b1nd.alimo.feature.onboarding.parent.login.first

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentLoginFirstBinding

class ParentLoginFirstFragment:BaseFragment<FragmentParentLoginFirstBinding, com.b1nd.alimo.feature.onboarding.parent.login.first.ParentLoginFirstViewModel>(
    R.layout.fragment_parent_login_first
) {
    override val viewModel: com.b1nd.alimo.feature.onboarding.parent.login.first.ParentLoginFirstViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}