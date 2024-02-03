package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentJoinFirstBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstViewModel.Companion.ON_CLICK_LOGIN
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstViewModel.Companion.ON_CLICK_NEXT
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstViewModel.Companion.ON_CLICK_STUDENT_CODE
import com.b1nd.alimo.presentation.utiles.onSuccessEvent

class ParentJoinFirstFragment : BaseFragment<FragmentParentJoinFirstBinding, ParentJoinFirstViewModel>(
    R.layout.fragment_parent_join_first
) {
    override val viewModel: ParentJoinFirstViewModel by viewModels()

    override fun initView() {
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }
                    ON_CLICK_LOGIN -> {
                        findNavController().navigate(R.id.action_parentJoinFirst_to_parentLoginFirst)
                    }
                    ON_CLICK_NEXT -> {
                        findNavController().navigate(R.id.action_parentJoinFirst_to_parentJoinSecond)
                    }
                    ON_CLICK_STUDENT_CODE -> {

                    }

                }
            }
        }

        mBinding.studentCode1.requestFocus()
        showKeyboard()


        // Apply GenericTextWatcher to handle text changes
        mBinding.studentCode1.addTextChangedListener(GenericTextWatcher(mBinding.studentCode1, mBinding.studentCode2))
        mBinding.studentCode2.addTextChangedListener(GenericTextWatcher(mBinding.studentCode2, mBinding.studentCode3))
        mBinding.studentCode3.addTextChangedListener(GenericTextWatcher(mBinding.studentCode3, mBinding.studentCode4))
        mBinding.studentCode4.addTextChangedListener(GenericTextWatcher(mBinding.studentCode4, mBinding.studentCode5))
        mBinding.studentCode5.addTextChangedListener(GenericTextWatcher(mBinding.studentCode5, mBinding.studentCode6))
        mBinding.studentCode6.addTextChangedListener(GenericTextWatcher(mBinding.studentCode6, mBinding.loginBtnOn))

        // Apply GenericKeyEvent to handle deletion
        mBinding.studentCode1.setOnKeyListener(GenericKeyEvent(mBinding.studentCode1, null))
        mBinding.studentCode2.setOnKeyListener(GenericKeyEvent(mBinding.studentCode2, mBinding.studentCode1))
        mBinding.studentCode3.setOnKeyListener(GenericKeyEvent(mBinding.studentCode3, mBinding.studentCode2))
        mBinding.studentCode4.setOnKeyListener(GenericKeyEvent(mBinding.studentCode4, mBinding.studentCode3))
        mBinding.studentCode5.setOnKeyListener(GenericKeyEvent(mBinding.studentCode5, mBinding.studentCode4))
        mBinding.studentCode6.setOnKeyListener(GenericKeyEvent(mBinding.studentCode6, mBinding.studentCode5))


        // Set up initial backgrounds
        setupEditTextBackground(mBinding.studentCode1)
        setupEditTextBackground(mBinding.studentCode2)
        setupEditTextBackground(mBinding.studentCode3)
        setupEditTextBackground(mBinding.studentCode4)
        setupEditTextBackground(mBinding.studentCode5)
        setupEditTextBackground(mBinding.studentCode6)
    }

    private fun setupEditTextBackground(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                updateEditTextBackground(editText)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // 이전 텍스트 변경 전에 수행할 작업
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // 텍스트가 변경될 때 수행할 작업
            }
        })

        // 초기 설정을 위해 한 번 호출
        updateEditTextBackground(editText)
    }

    private fun updateEditTextBackground(editText: EditText) {
        val backgroundResource = if (editText.text?.isEmpty() == true) {
            R.drawable.edittext_border_background
        } else {
            R.drawable.otp_edittext_border_background
        }

        editText.setBackgroundResource(backgroundResource)
    }

    inner class GenericTextWatcher(private val currentView: EditText, private val nextView: View?) : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            if (editable.length == 1) {
                nextView?.let {
                    it.requestFocus()
                    if (it is EditText) {
                        // 텍스트가 입력되면서 다음 EditText일 경우
                        updateEditTextBackground(it)
                    } else if (it is Button) {
                        // 텍스트가 입력되면서 다음 Button일 경우
                        hideKeyboard() // 키보드 숨김
                        mBinding.loginBtnOff.visibility = View.INVISIBLE
                        mBinding.loginBtnOn.visibility = View.VISIBLE
                    }
                }
            }
            updateEditTextBackground(currentView)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // 변경 전에 수행할 작업
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // 텍스트가 변경될 때 수행할 작업
        }
    }

    inner class GenericKeyEvent(private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener {
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.text.isEmpty()) {
                // 현재가 비어 있으면 이전 EditText의 숫자도 삭제됨
                previousView?.text = null
                previousView?.requestFocus()
                updateEditTextBackground(previousView!!)
                return true
            }
            return false
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
    private fun showKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mBinding.studentCode1, InputMethodManager.SHOW_IMPLICIT)
    }
}