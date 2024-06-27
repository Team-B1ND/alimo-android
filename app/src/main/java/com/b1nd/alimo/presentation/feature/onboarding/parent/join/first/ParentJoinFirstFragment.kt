package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentJoinFirstBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstViewModel.Companion.ON_CLICK_LOGIN
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstViewModel.Companion.ON_CLICK_NEXT
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.first.ParentJoinFirstViewModel.Companion.ON_CLICK_STUDENT_CODE
import com.b1nd.alimo.presentation.utiles.Dlog
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.setOnPasteListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParentJoinFirstFragment :
    BaseFragment<FragmentParentJoinFirstBinding, ParentJoinFirstViewModel>(
        R.layout.fragment_parent_join_first
    ) {
    override val viewModel: ParentJoinFirstViewModel by viewModels()
    private val dialog: IncorrectCodeDialog by lazy {
        IncorrectCodeDialog()
    }
    private val editTextList = mutableListOf<EditText>()

    override fun initView() {
        initSideEffect()

        collectFlow(viewModel.isButtonClicked){
            if (it){
                mBinding.progressCir.visibility = View.VISIBLE
                mBinding.loginBtnOn.visibility = View.INVISIBLE
                mBinding.progressCir.setIndeterminate(it)
            }else{
                mBinding.progressCir.visibility = View.INVISIBLE
                mBinding.loginBtnOn.visibility = View.VISIBLE
            }
        }

        lifecycleScope.launch {
            // 학생코드가 올바른 지 확인
            viewModel.trueFalse.collect { studentCode ->
                val d = studentCode
                // 올바르지 않다면 리턴
                if (d.memberId != null){
                    Dlog.d("initView: ${getChildCode()} ${d.memberId} ")
                    val direction =
                        ParentJoinFirstFragmentDirections.actionParentJoinFirstToParentJoinSecond(
                            getChildCode(),
                            d.memberId,
                        )
                    findNavController().navigate(direction)
                }
            }
        }

        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_BACK -> {
                        Dlog.d("initView: 뒤로가")
                        findNavController().navigate(R.id.action_parentJoinFirst_to_onboardingThird)
                    }

                    ON_CLICK_LOGIN -> {
                        findNavController().navigate(R.id.action_parentJoinFirst_to_parentLoginFirst)
                    }

                    ON_CLICK_NEXT -> {
                        val studentCode = getChildCode()
                        Dlog.d("학생 코드: $studentCode")
                        viewModel.checkStudentCode(studentCode)
                    }

                    ON_CLICK_STUDENT_CODE -> {
                        val url = "https://subsequent-grouse.super.site/"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    }

                    ON_CLICK_BACKGROUND -> {
                        view?.hideKeyboard()
                    }
                }
            }
        }

        mBinding.studentCode1.requestFocus()
        showKeyboard()

        editTextList.addAll(
            listOf(
                mBinding.studentCode1,
                mBinding.studentCode2,
                mBinding.studentCode3,
                mBinding.studentCode4,
                mBinding.studentCode5,
                mBinding.studentCode6
            )
        )

        editTextList.forEachIndexed { index, editText ->
            editText.addTextChangedListener(
                GenericTextWatcher(
                    editText,
                    editTextList.getOrNull(index + 1)
                )
            )

            editText.setOnKeyListener(GenericKeyEvent(editText, editTextList.getOrNull(index - 1)))

            setupEditTextBackground(editText)
        }

        // 첫 번째 EditText에 붙여넣기 리스너 설정
        mBinding.studentCode1.setOnPasteListener { pastedText ->
            if (pastedText.length == editTextList.size) {
                for (i in editTextList.indices) {
                    editTextList[i].setText(pastedText[i].toString())
                }
                view?.hideKeyboard()
                mBinding.loginBtnOff.visibility = View.INVISIBLE
                mBinding.loginBtnOn.visibility = View.VISIBLE
            }
        }
    }

    private fun getChildCode() = StringBuilder().apply {
        append(mBinding.studentCode1.text.toString())
        append(mBinding.studentCode2.text.toString())
        append(mBinding.studentCode3.text.toString())
        append(mBinding.studentCode4.text.toString())
        append(mBinding.studentCode5.text.toString())
        append(mBinding.studentCode6.text.toString())
    }.toString()

    private fun setupEditTextBackground(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                updateEditTextBackground(editText)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

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

    inner class GenericTextWatcher(private val currentView: EditText, private val nextView: View?) :
        TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            if (isProgressBarVisible()) return

            if (editable.length == 1) {
                if (checkAllText()) {
                    view?.hideKeyboard()
                    mBinding.loginBtnOff.visibility = View.INVISIBLE
                    mBinding.loginBtnOn.visibility = View.VISIBLE
                } else {
                    nextView?.requestFocus()
                    mBinding.loginBtnOff.visibility = View.VISIBLE
                    mBinding.loginBtnOn.visibility = View.INVISIBLE
                    if (nextView is EditText) {
                        updateEditTextBackground(nextView)
                    }
                }
            }
            updateEditTextBackground(currentView)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        private fun checkAllText(): Boolean {
            for (editText in editTextList) {
                if (editText.text.isNullOrEmpty()) {
                    return false
                }
            }
            return true
        }
    }

    inner class GenericKeyEvent(
        private val currentView: EditText,
        private val previousView: EditText?
    ) : View.OnKeyListener {
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (isProgressBarVisible()) return false

            updateButtonState()
            if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.text.isEmpty()) {
                previousView?.text = null
                previousView?.requestFocus()
                if (previousView != null) {
                    updateEditTextBackground(previousView)
                }
                return true
            }
            return false
        }

        private fun updateButtonState() {
            val allTextFilled = editTextList.all { it.text.isNotEmpty() }
            if (allTextFilled) {
                mBinding.loginBtnOff.visibility = View.INVISIBLE
                mBinding.loginBtnOn.visibility = View.VISIBLE
            } else {
                mBinding.loginBtnOff.visibility = View.VISIBLE
                mBinding.loginBtnOn.visibility = View.INVISIBLE
            }
        }
    }

    private fun showKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mBinding.studentCode1, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun initSideEffect() {
        collectFlow(viewModel.parentJoinFirstSideEffect) {
            when(it) {
                is ParentJoinFirstSideEffect.FailedChildCode -> {
                    dialog.show(requireActivity().supportFragmentManager, "올바르지 않은 학생코드")
                }
            }
        }
    }

    private fun isProgressBarVisible(): Boolean =
        mBinding.progressCir.visibility == View.VISIBLE
}





