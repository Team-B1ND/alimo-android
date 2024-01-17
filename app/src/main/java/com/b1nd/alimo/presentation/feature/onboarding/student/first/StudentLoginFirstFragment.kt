package com.b1nd.alimo.presentation.feature.onboarding.student.first

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentStudentLoginFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentLoginFirstFragment:
    BaseFragment<FragmentStudentLoginFirstBinding, StudentLoginViewModel>(
    R.layout.fragment_student_login_first
) {
    override val viewModel: StudentLoginViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}