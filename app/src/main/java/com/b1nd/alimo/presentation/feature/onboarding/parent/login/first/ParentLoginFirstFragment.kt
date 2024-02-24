package com.b1nd.alimo.presentation.feature.onboarding.parent.login.first

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentLoginFirstBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.parent.login.first.ParentLoginFirstViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.login.first.ParentLoginFirstViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.parent.login.first.ParentLoginFirstViewModel.Companion.ON_CLICK_FIND_PW
import com.b1nd.alimo.presentation.feature.onboarding.parent.login.first.ParentLoginFirstViewModel.Companion.ON_CLICK_JOIN
import com.b1nd.alimo.presentation.feature.onboarding.parent.login.first.ParentLoginFirstViewModel.Companion.ON_CLICK_LOGIN
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentLoginFirstFragment:
    BaseFragment<FragmentParentLoginFirstBinding, ParentLoginFirstViewModel>(
    R.layout.fragment_parent_login_first
) {
    override val viewModel: ParentLoginFirstViewModel by viewModels()

    override fun initView() {
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when(it){
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }
                    ON_CLICK_LOGIN -> {

                    }
                    ON_CLICK_BACKGROUND -> {
                        Log.d("TAG", "initView: background")
                        mBinding.idEditTextLayout.clearFocus()
                        mBinding.pwEditTextLayout.clearFocus()
                        view?.hideKeyboard()
                    }
                    ON_CLICK_JOIN -> {
                        findNavController().navigate(R.id.action_parentLoginFirst_to_parentJoinFirst)
                    }
                    ON_CLICK_FIND_PW -> {
                        findNavController().navigate(R.id.action_parentLoginFirst_to_parentFindPWFirst)
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
            mBinding.loginBtnOff.visibility = View.INVISIBLE
            mBinding.loginBtnOn.visibility = View.VISIBLE
        } else {
            Log.d("TAG", "updateButtonColor: off")
            // 두 EditText 중 하나라도 텍스트가 null일 때 버튼의 색상을 기본 색상으로 변경
            mBinding.loginBtnOff.visibility = View.VISIBLE
            mBinding.loginBtnOn.visibility = View.INVISIBLE

        }
    }


}