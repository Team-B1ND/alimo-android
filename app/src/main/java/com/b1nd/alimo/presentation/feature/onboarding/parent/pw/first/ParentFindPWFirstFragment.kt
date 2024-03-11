package com.b1nd.alimo.presentation.feature.onboarding.parent.pw.first

import android.os.CountDownTimer
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                        mBinding.nextBtnOff.visibility = View.INVISIBLE
                        mBinding.nextBtnOn.visibility = View.VISIBLE
                        view?.hideKeyboard()
                    }
                }
            }
        }



    }
}