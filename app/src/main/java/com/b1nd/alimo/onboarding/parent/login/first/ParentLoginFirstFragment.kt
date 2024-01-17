package com.b1nd.alimo.onboarding.parent.login.first

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentLoginFirstBinding

class ParentLoginFirstFragment:BaseFragment<FragmentParentLoginFirstBinding, ParentLoginFirstViewModel>(
    R.layout.fragment_parent_login_first
) {
    override val viewModel: ParentLoginFirstViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}