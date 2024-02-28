package com.b1nd.alimo.presentation.feature.onboarding.parent.join.second

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentJoinSecondBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.second.ParentJoinSecondViewModel.Companion.FAILURE
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.second.ParentJoinSecondViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.second.ParentJoinSecondViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.second.ParentJoinSecondViewModel.Companion.ON_CLICK_LOGIN
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.second.ParentJoinSecondViewModel.Companion.ON_CLICK_NEXT
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.second.ParentJoinSecondViewModel.Companion.SUCCESS
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParentJoinSecondFragment :
    BaseFragment<FragmentParentJoinSecondBinding, ParentJoinSecondViewModel>(
        R.layout.fragment_parent_join_second
    ) {
    override val viewModel: ParentJoinSecondViewModel by viewModels()
    private val args: ParentJoinSecondFragmentArgs by navArgs()


    override fun initView() {
        viewModel.setStudentCode(args.childeCode)

        lifecycleScope.launch {
            viewModel.memberName.collect{
                mBinding.parentName.text = it.name
            }
        }

        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack(R.id.parentJoinSecond, false)
                        findNavController().navigate(R.id.action_parentJoinSecond_to_onboardingThird)
                    }

                    ON_CLICK_LOGIN -> {
                        findNavController().navigate(R.id.action_parentJoinSecond_to_parentLoginFirst)
                    }

                    ON_CLICK_NEXT -> {
                        if (comparisonPassword()) {
//                            findNavController().navigate(R.id.action_parentJoinSecond_to_parentJoinThird)
                            viewModel.singUp(
                                email = mBinding.idEditText.text.toString(),
                                password = mBinding.pwEditText.text.toString(),
                                childCode = args.childeCode,
                                memberId = args.memberId
                            )
                        } else {
                            mBinding.errorText.visibility = View.VISIBLE
                        }
                    }

                    ON_CLICK_BACKGROUND -> {
                        Log.d("TAG", "initView: background")
                        mBinding.idEditTextLayout.clearFocus()
                        mBinding.pwEditTextLayout.clearFocus()
                        view?.hideKeyboard()
                    }

                    SUCCESS -> {
                        Log.d("TAG", "initView: 로그인 성공")
                        val email = mBinding.idEditText.text.toString()
                        val direction = ParentJoinSecondFragmentDirections.actionParentJoinSecondToParentJoinThird(
                            email
                        )
                        findNavController().navigate(direction)
                    }
                    FAILURE ->{
                        Log.d("TAG", "initView: 로그인 실패")

                    }


                }
            }
        }
        mBinding.idEditTextLayout.setEndIconOnClickListener {
            mBinding.idEditText.text = null
        }

        // TextWatcher를 이용하여 EditText의 텍스트 변화 감지
        mBinding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                updateButtonColor()
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        mBinding.pwEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                updateButtonColor()
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        mBinding.verifyPwEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                updateButtonColor()
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }

    private fun updateButtonColor() {
        val text1 = mBinding.idEditText.text.toString().trim { it <= ' ' }
        val text2 = mBinding.pwEditText.text.toString().trim { it <= ' ' }
        val text3 = mBinding.verifyPwEditText.text.toString().trim { it <= ' ' }

        // 버튼의 색상을 변경하는 로직 추가
        if (text1.isNotEmpty() && text2.isNotEmpty() && text3.isNotEmpty()) {
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

    private fun comparisonPassword(): Boolean {
        val password = mBinding.pwEditText.text.toString()
        val verifyPassword = mBinding.verifyPwEditText.text.toString()
        Log.d("TAG", "comparisonPassword: ${ password == verifyPassword }")
        return password == verifyPassword
    }


}