package com.b1nd.alimo.presentation.feature.onboarding.parent.login.first

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentLoginFirstBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.main.MainActivity
import com.b1nd.alimo.presentation.feature.onboarding.parent.login.first.ParentLoginFirstViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.login.first.ParentLoginFirstViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.parent.login.first.ParentLoginFirstViewModel.Companion.ON_CLICK_FIND_PW
import com.b1nd.alimo.presentation.feature.onboarding.parent.login.first.ParentLoginFirstViewModel.Companion.ON_CLICK_JOIN
import com.b1nd.alimo.presentation.feature.onboarding.parent.login.first.ParentLoginFirstViewModel.Companion.ON_CLICK_LOGIN
import com.b1nd.alimo.presentation.utiles.Dlog
import com.b1nd.alimo.presentation.utiles.Env
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.shortToast
import com.b1nd.alimo.presentation.utiles.startActivityWithFinishAll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParentLoginFirstFragment :
    BaseFragment<FragmentParentLoginFirstBinding, ParentLoginFirstViewModel>(
        R.layout.fragment_parent_login_first
    ) {
    override val viewModel: ParentLoginFirstViewModel by viewModels()

    override fun initView() {
        initSideEffect()

        collectFlow(viewModel.isButtonClicked) {
            if (it) {
                mBinding.progressCir.visibility = View.VISIBLE
                mBinding.loginBtnOn.visibility = View.INVISIBLE
                mBinding.progressCir.setIndeterminate(it)
                mBinding.idEditText.isEnabled = false // EditText 비활성화
                mBinding.pwEditText.isEnabled = false // EditText 비활성화
            } else {
                mBinding.progressCir.visibility = View.INVISIBLE
                mBinding.loginBtnOn.visibility = View.VISIBLE
                mBinding.idEditText.isEnabled = true // EditText 활성화
                mBinding.pwEditText.isEnabled = true // EditText 활성화
            }
        }


        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_BACK -> {
                        findNavController().navigate(R.id.action_parentLoginFirst_to_onboardingThird)
                    }
                    ON_CLICK_LOGIN -> {
                        viewModel.login(
                            mBinding.idEditText.text.toString(),
                            mBinding.pwEditText.text.toString()
                        )
                    }
                    ON_CLICK_BACKGROUND -> {
                        Dlog.d("initView: background")
                        mBinding.idEditTextLayout.clearFocus()
                        mBinding.pwEditTextLayout.clearFocus()
                        view?.hideKeyboard()
                    }
                    ON_CLICK_JOIN -> {
                        findNavController().navigate(R.id.action_parentLoginFirst_to_parentJoinFirst)
                    }
                    ON_CLICK_FIND_PW -> {
                        requireContext().shortToast("추후 업데이트 될 예정입니다")
                    }
                }
            }
        }

        // 로그인을 성공하면 MainActivity로 이동
        lifecycleScope.launch {
            viewModel.loginState.collect {
                if (it.refreshToken != null && it.accessToken != null) {
                    startActivityWithFinishAll(MainActivity::class.java)
                }
            }
        }

        // Text Delete Icon Click Event
        mBinding.idEditTextLayout.setEndIconOnClickListener {
            mBinding.idEditTextLayout.editText?.text = null
        }

        // TextWatcher를 이용하여 EditText의 텍스트 변화 감지
        mBinding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isProgressBarVisible()) {
                    updateButtonColor()
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        mBinding.pwEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isProgressBarVisible()) {
                    updateButtonColor()
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }

    // InputTextLayout에 글자가 있다면 다음 Fragment로 가는 버튼 활성화
    private fun updateButtonColor() {
        if (isProgressBarVisible()) return // 프로그레스바가 보이면 종료

        val text1 = mBinding.idEditText.text.toString().trim { it <= ' ' }
        val text2 = mBinding.pwEditText.text.toString().trim { it <= ' ' }

        // 버튼의 색상을 변경하는 로직 추가
        if (text1.isNotEmpty() && text2.isNotEmpty()) {
            Dlog.d("updateButtonColor: on")
            // 두 EditText의 텍스트가 null이 아닐 때 버튼의 색상을 변경
            mBinding.loginBtnOff.visibility = View.INVISIBLE
            mBinding.loginBtnOn.visibility = View.VISIBLE
        } else {
            Dlog.d("updateButtonColor: off")
            // 두 EditText 중 하나라도 텍스트가 null일 때 버튼의 색상을 기본 색상으로 변경
            mBinding.loginBtnOff.visibility = View.VISIBLE
            mBinding.loginBtnOn.visibility = View.INVISIBLE
        }
    }

    private fun initSideEffect() {
        collectFlow(viewModel.parentLoginSideEffect) {
            when (it) {
                is ParentLoginSideEffect.FailedLogin -> {
                    requireContext().shortToast("아이디와 비밀번호를 다시 확인해주세요")
                }
                is ParentLoginSideEffect.FailedLoadFcmToken -> {
                    requireContext().shortToast(Env.ERROR)
                }
            }
        }
    }

    private fun isProgressBarVisible(): Boolean =
        mBinding.progressCir.visibility == View.VISIBLE
    
}
