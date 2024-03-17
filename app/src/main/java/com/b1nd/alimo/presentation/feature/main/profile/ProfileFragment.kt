package com.b1nd.alimo.presentation.feature.main.profile

import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.b1nd.alimo.BuildConfig
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentProfileBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.custom.CustomCategoryCard
import com.b1nd.alimo.presentation.custom.CustomSnackBar
import com.b1nd.alimo.presentation.feature.main.profile.ProfileViewModel.Companion.ON_CLICK_LOGOUT
import com.b1nd.alimo.presentation.feature.main.profile.ProfileViewModel.Companion.ON_CLICK_PRIVATE_POLICY
import com.b1nd.alimo.presentation.feature.main.profile.ProfileViewModel.Companion.ON_CLICK_SERVICE_POLICY
import com.b1nd.alimo.presentation.feature.main.profile.ProfileViewModel.Companion.ON_CLICK_STUDENT_CODE
import com.b1nd.alimo.presentation.feature.main.profile.ProfileViewModel.Companion.ON_CLICK_WITHDRAWAL
import com.b1nd.alimo.presentation.feature.main.profile.student.ProfileStudentClickListener
import com.b1nd.alimo.presentation.feature.main.profile.student.ProfileStudentCodeDialog
import com.b1nd.alimo.presentation.feature.main.profile.withdrawal.ProfileWithdrawalClickListener
import com.b1nd.alimo.presentation.feature.main.profile.withdrawal.ProfileWithdrawalDialog
import com.b1nd.alimo.presentation.feature.onboarding.OnboardingActivity
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.collectStateFlow
import com.b1nd.alimo.presentation.utiles.loadImage
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.shortToast
import com.b1nd.alimo.presentation.utiles.startActivityWithFinishAll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment:
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile),
    ProfileStudentClickListener,
    ProfileWithdrawalClickListener
{

    override val viewModel: ProfileViewModel by viewModels()
    private val dialog: ProfileStudentCodeDialog by lazy {
        ProfileStudentCodeDialog(this, viewModel.state.value.data?.childCode)
    }

    private val withdrawalDialog: ProfileWithdrawalDialog by lazy {
        ProfileWithdrawalDialog(this)
    }
    override fun initView() {
        observeState()
        initProfile()
        initSideEffect()
        initProfileText()
        initAlarm()
        initLogout()

        bindingViewEvent {  event ->
            event.onSuccessEvent {
                when(it) {
                    ON_CLICK_STUDENT_CODE -> {
                        dialog.show(super.getChildFragmentManager(), "dialog")
                    }
                    ON_CLICK_PRIVATE_POLICY -> {

                    }
                    ON_CLICK_SERVICE_POLICY -> {

                    }
                    ON_CLICK_LOGOUT -> {
                        viewModel.logout()
                    }
                    ON_CLICK_WITHDRAWAL -> {
                        withdrawalDialog.show(super.getChildFragmentManager(), "withdrawalDialog")
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.loadProfile()
    }

    private fun initProfile() {
        collectStateFlow(viewModel.state) {
            lifecycleScope.launch(Dispatchers.Main) {
                it.data?.let { model ->
                    if (model.image != null) {
                        Log.d("TAG", "initView: 엄 이미지 바ㅏ인딩")
                        mBinding.imageProfile.loadImage(model.image)
                    }
                    if (model.childCode != null) {
                        mBinding.textStudentCode.visibility = View.VISIBLE
                    }
                    mBinding.textUserName.text = model.name
                }
                if (!it.isAdd) {
                    viewModel.addCategory()
                    mBinding.layoutCategory.removeAllViews()
                    it.category?.forEach { name ->
                        Log.d("TAG", "initView: testss")
                        val card = CustomCategoryCard(requireContext(), null, name)
                        mBinding.layoutCategory.addView(card)
                    }
                }
            }

        }
    }

    private fun initSideEffect() {
        collectFlow(viewModel.sideEffect) {
            when(it) {
                is ProfileSideEffect.Success -> {

                }
                is ProfileSideEffect.FailedLoad -> {
                    Toast.makeText(requireContext(), "로딩에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
                is ProfileSideEffect.FailedWithdrawal -> {
                    requireContext().shortToast("회원탈퇴에 실패하였습니다.")
                }
                is ProfileSideEffect.SuccessWithdrawal -> {
                    requireContext().shortToast("회원탈퇴에 성공하였습니다.")
                }
            }
        }
    }

    private fun initProfileText() {
        mBinding.cardVersion.setDescriptionText(BuildConfig.VERSION_NAME)
        mBinding.textStudentCode.paintFlags = Paint.UNDERLINE_TEXT_FLAG
    }

    private fun initAlarm() {
        // 알람 상태 불러오기
        viewModel.loadAlarm()
        // 알림 설정을 바꾸면 저장
        mBinding.cardAlarm.setSwitchOnClickListener {
            Log.d("TAG", "initView: $it")
            viewModel.setAlarmState(it)
        }
    }

    private fun initLogout() {
        // 로그아웃 상태 추적
        collectStateFlow(viewModel.logoutState) {
            if (it) {
                startActivityWithFinishAll(OnboardingActivity::class.java)
            }
        }
    }

    private fun observeState() {
        // 현재 알림 확인후 설기
        collectStateFlow(viewModel.settingState) {
            mBinding.cardAlarm.setSwitchChecked(it)
        }


    }

    override fun onCopy() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            CustomSnackBar(requireView(), "복사에 성공하였습니다!").show()
        }
    }

    override fun onWithdrawal() {
        viewModel.withdrawal()
    }
}