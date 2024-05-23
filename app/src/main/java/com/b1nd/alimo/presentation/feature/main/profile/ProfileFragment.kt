package com.b1nd.alimo.presentation.feature.main.profile

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.view.View
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
        initProfile()
        initSideEffect()
        initProfileText()
        initAlarm()

        bindingViewEvent {  event ->
            event.onSuccessEvent {
                when(it) {
                    ON_CLICK_STUDENT_CODE -> {
                        if (dialog.isAdded) {
                            return@onSuccessEvent
                        }
                        dialog.show(super.getChildFragmentManager(), "student_dialog")
                    }
                    ON_CLICK_PRIVATE_POLICY -> {
                        val url = "https://ahead-yacht-97a.notion.site/215ee2a6510e4cff92cff58f7c5011de?pvs=4"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)

                    }
                    ON_CLICK_SERVICE_POLICY -> {
                        val url = "https://ahead-yacht-97a.notion.site/e9ae676d8ecd4a9a921400ceea6c27e3?pvs=4"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)

                    }
                    ON_CLICK_LOGOUT -> {
                        viewModel.logout()
                    }
                    ON_CLICK_WITHDRAWAL -> {
                        if (withdrawalDialog.isAdded) {
                            return@onSuccessEvent
                        }
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
                        mBinding.imageProfile.loadImage(model.image)
                    }
                    if (model.childCode != null) {
                        mBinding.textStudentCode.visibility = View.VISIBLE
                    }
                    mBinding.textUserName.text = model.name
                }
                // 중복 카테고리 추가 방지
                if (!it.isAdd) {
                    viewModel.addCategory()
                    mBinding.layoutCategory.removeAllViews()
                    it.category.forEach { name ->
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
                    requireContext().shortToast("로딩에 실패하였습니다.")
                }
                is ProfileSideEffect.FailedWithdrawal -> {
                    requireContext().shortToast("회원탈퇴에 실패하였습니다.")
                }
                is ProfileSideEffect.SuccessWithdrawal -> {
                    requireContext().shortToast("회원탈퇴에 성공하였습니다.")
                    startActivityWithFinishAll(OnboardingActivity::class.java)
                }
                is ProfileSideEffect.FailedLoadCategory -> {
                    requireContext().shortToast("카테고리를 불러오는데 실패하였습니다.")
                }
                is ProfileSideEffect.FailedLoadInfo -> {
                    requireContext().shortToast("정보를 불러오는데 실패하였습니다.")
                }
                is ProfileSideEffect.SuccessLogout -> {
                    startActivityWithFinishAll(OnboardingActivity::class.java)
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

        // 알람 상태에 따라 반영
        collectStateFlow(viewModel.alarmState) {
            mBinding.cardAlarm.setSwitchChecked(it)
        }
        // 알림 설정을 바꾸면 저장
        mBinding.cardAlarm.setSwitchOnClickListener {
            viewModel.setAlarmState(it)
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