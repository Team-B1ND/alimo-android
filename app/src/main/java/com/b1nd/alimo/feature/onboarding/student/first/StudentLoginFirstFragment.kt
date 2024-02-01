package com.b1nd.alimo.feature.onboarding.student.first

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentStudentLoginFirstBinding
import com.b1nd.alimo.feature.onboarding.student.first.StudentLoginViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.feature.onboarding.student.first.StudentLoginViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.feature.onboarding.student.first.StudentLoginViewModel.Companion.ON_CLICK_LOGIN_ON
import com.b1nd.alimo.utiles.onSuccessEvent
import com.google.android.material.internal.ViewUtils.hideKeyboard

class StudentLoginFirstFragment:BaseFragment<FragmentStudentLoginFirstBinding, StudentLoginViewModel>(
    R.layout.fragment_student_login_first
) {
    override val viewModel: StudentLoginViewModel by viewModels()

    override fun initView() {
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when(it){
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }
                    ON_CLICK_LOGIN_ON -> {

                    }
                    ON_CLICK_BACKGROUND -> {
                        Log.d("TAG", "initView: background")
                        mBinding.idEditText.clearFocus()
                        mBinding.pwEditText.clearFocus()
                        hideKeyboard()
                    }
                }
            }
        }
        mBinding.idEditTextLayout.setEndIconOnClickListener {
            mBinding.idEditTextLayout.editText?.text = null
        }
        // TextWatcher를 이용하여 EditText의 텍스트 변화 감지
        mBinding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonColor()
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        mBinding.pwEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonColor()
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }

    private fun updateButtonColor() {
        val text1 = mBinding.idEditText.text.toString().trim { it <= ' ' }
        val text2 = mBinding.pwEditText.text.toString().trim { it <= ' ' }

        // 버튼의 색상을 변경하는 로직 추가
        if (text1.isNotEmpty() && text2.isNotEmpty()) {
            Log.d("TAG", "updateButtonColor: on")
            // 두 EditText의 텍스트가 null이 아닐 때 버튼의 색상을 변경
            mBinding.loginBtnOff.visibility = View.GONE
            mBinding.loginBtnOn.visibility = View.VISIBLE
            hideKeyboard()
        } else {
            Log.d("TAG", "updateButtonColor: off")
            // 두 EditText 중 하나라도 텍스트가 null일 때 버튼의 색상을 기본 색상으로 변경
            mBinding.loginBtnOff.visibility = View.VISIBLE
            mBinding.loginBtnOn.visibility = View.GONE

        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}