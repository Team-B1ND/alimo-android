package com.b1nd.alimo.presentation.feature.onboarding.parent.pw.first

import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentFindPwFirstBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel
import com.b1nd.alimo.presentation.feature.onboarding.parent.pw.first.ParentFindPWFirstViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.pw.first.ParentFindPWFirstViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.parent.pw.first.ParentFindPWFirstViewModel.Companion.ON_CLICK_CERTIFICATION
import com.b1nd.alimo.presentation.feature.onboarding.parent.pw.first.ParentFindPWFirstViewModel.Companion.ON_CLICK_NEXT
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent

class ParentFindPWFirstFragment :
    BaseFragment<FragmentParentFindPwFirstBinding, ParentFindPWFirstViewModel>(
        R.layout.fragment_parent_find_pw_first
    ) {
    override val viewModel: ParentFindPWFirstViewModel by viewModels()

    override fun initView() {
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }

                    ON_CLICK_BACKGROUND -> {
                        mBinding.idEditTextLayout.clearFocus()
                        view?.hideKeyboard()
                    }

                    ON_CLICK_NEXT -> {
                        findNavController().navigate(R.id.action_parentFindPWFirst_to_parentFindPWSecond)
                    }

                    ON_CLICK_CERTIFICATION -> {
                        mBinding.check.visibility = View.VISIBLE
                        mBinding.time.visibility = View.VISIBLE
                        mBinding.certification.visibility = View.GONE


                        object : CountDownTimer(300000, 1000) {

                            override fun onTick(millisUntilFinished: Long) {
                                val minutes = millisUntilFinished / 60000
                                val seconds = (millisUntilFinished % 60000) / 1000
                                val timeString = String.format("%d:%02d", minutes, seconds)
                                mBinding.time.text = timeString
                            }

                            override fun onFinish() {
                                mBinding.time.text = "0:00"
                            }

                        }.start()
                    }

                    ParentJoinThirdViewModel.ON_CLICK_CHECK -> {

                    }
                }
            }
        }

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

        mBinding.checkLayout.bringToFront()


    }

    private fun updateButtonColor() {
        val text1 = mBinding.idEditText.text.toString().length


        // 버튼의 색상을 변경하는 로직 추가
        if (text1 == 6) {
            Log.d("TAG", "updateButtonColor: on")
            // 두 EditText의 텍스트가 null이 아닐 때 버튼의 색상을 변경
            mBinding.nextBtnOff.visibility = View.INVISIBLE
            mBinding.nextBtnOn.visibility = View.VISIBLE
        } else {
            Log.d("TAG", "updateButtonColor: off")
            // 두 EditText 중 하나라도 텍스트가 null일 때 버튼의 색상을 기본 색상으로 변경
            mBinding.nextBtnOff.visibility = View.VISIBLE
            mBinding.nextBtnOn.visibility = View.INVISIBLE

        }
    }



}