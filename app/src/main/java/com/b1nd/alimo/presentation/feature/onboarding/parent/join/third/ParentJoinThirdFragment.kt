package com.b1nd.alimo.feature.onboarding.parent.join.third

import android.content.Context
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentParentJoinThirdBinding
import com.b1nd.alimo.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_CERTIFICATION
import com.b1nd.alimo.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_CHECK
import com.b1nd.alimo.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_JOIN
import com.b1nd.alimo.utiles.onSuccessEvent
import java.util.Timer

class ParentJoinThirdFragment:BaseFragment<FragmentParentJoinThirdBinding, ParentJoinThirdViewModel>(
    R.layout.fragment_parent_join_third
) {
    override val viewModel: ParentJoinThirdViewModel by viewModels()



    override fun initView() {
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when(it){
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }
                    ON_CLICK_BACKGROUND -> {
                        mBinding.idEditTextLayout.clearFocus()
                        hideKeyboard()
                    }
                    ON_CLICK_JOIN -> {
                        findNavController().navigate(R.id.action_parentJoinThirst_to_onboardingThird)
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
                    ON_CLICK_CHECK -> {

                    }
                }
            }
        }

        mBinding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
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
            mBinding.joinBtnOff.visibility = View.INVISIBLE
            mBinding.joinBtnOn.visibility = View.VISIBLE
            hideKeyboard()
        } else {
            Log.d("TAG", "updateButtonColor: off")
            // 두 EditText 중 하나라도 텍스트가 null일 때 버튼의 색상을 기본 색상으로 변경
            mBinding.joinBtnOff.visibility = View.VISIBLE
            mBinding.joinBtnOn.visibility = View.INVISIBLE

        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}