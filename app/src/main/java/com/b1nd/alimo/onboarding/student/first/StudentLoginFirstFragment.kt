package com.b1nd.alimo.onboarding.student.first

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentStudentLoginFirstBinding

class StudentLoginFirstFragment:BaseFragment<FragmentStudentLoginFirstBinding, StudentLoginViewModel>(
    R.layout.fragment_student_login_first
) {
    override val viewModel: StudentLoginViewModel by viewModels()

    override fun initView() {
//        TODO("Not yet implemented")
    }
}