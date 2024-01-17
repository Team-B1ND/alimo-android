package com.b1nd.alimo.feature.onboarding.student.first

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentStudentLoginFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentLoginFirstFragment:BaseFragment<FragmentStudentLoginFirstBinding, com.b1nd.alimo.feature.onboarding.student.first.StudentLoginViewModel>(
    R.layout.fragment_student_login_first
) {
    override val viewModel: com.b1nd.alimo.feature.onboarding.student.first.StudentLoginViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}