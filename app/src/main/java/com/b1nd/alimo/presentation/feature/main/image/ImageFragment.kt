package com.b1nd.alimo.presentation.feature.main.image

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.data.model.FileModel
import com.b1nd.alimo.databinding.FragmentImageBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.main.MainActivity
import com.b1nd.alimo.presentation.feature.main.image.ImageViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.main.image.ImageViewModel.Companion.ON_CLICK_DOWNLOAD
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.systemBarDark
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFragment constructor(
    private val notificationId: Int,
    private val itemCount: Int,
    private val itemIndex: Int
): BaseFragment<FragmentImageBinding, ImageViewModel>(R.layout.fragment_image) {

    override val viewModel: ImageViewModel by viewModels()

    override fun initView() {
        (requireActivity() as? MainActivity)?.bottomVisible(false)
        initTouch()
        initPager()
        initBar()

        Log.d("TAG", "initView: $notificationId $itemCount $itemIndex")

        bindingViewEvent {
            it.onSuccessEvent {
                when(it) {
                    ON_CLICK_BACK -> {
                        parentFragmentManager.popBackStack()
                    }
                    ON_CLICK_DOWNLOAD -> {

                    }
                }
            }
        }
    }

    private fun initBar() {
        mBinding.textCount.text = "${itemIndex+1}/${itemCount}"
    }

    private fun initPager() {
        val adapter = ImageViewPagerAdapter(
            listOf(
                FileModel(
                    "https://s3-alimo.s3.ap-northeast-2.amazonaws.com/notification_images/e314d7ca-a429-4358-bea0-c6889f14834b_A4_-_1.png",
                    "",
                    0,
                    "",
                    ""
                ),
                FileModel(
                    "https://s3-alimo.s3.ap-northeast-2.amazonaws.com/notification_images/541887d8-1dc3-450e-8414-78e9f5e0932c_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-03-04_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_7.10.07.png",
                    "",
                    0,
                    "",
                    ""
                ),
                FileModel(
                    "https://dodam.kr.object.ncloudstorage.com/dodam/35b5f7bd-2462-4ab3-b6c5-662e25ca5e9cearth.jpg",
                    "",
                    0,
                    "",
                    ""
                ),
            )
        ) {
            visibilityChangeLayout()
        }
        mBinding.pagerImage.adapter = adapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouch() {
        mBinding.layoutParent.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                visibilityChangeLayout()
            }
            return@setOnTouchListener true
        }
        mBinding.pagerImage.setOnClickListener {
            visibilityChangeLayout()
        }
        mBinding.pagerImage.setOnTouchListener { view, motionEvent ->
            Log.d("TAG", "initTouch: teest")
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                visibilityChangeLayout()
            }
            return@setOnTouchListener true
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as? MainActivity)?.bottomVisible(false)
        systemBarDark(true)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        systemBarDark(false)
    }

    private fun visibilityChangeLayout() {
        mBinding.run {
            layoutTopBar.visibility = if (layoutTopBar.isVisible) View.INVISIBLE else View.VISIBLE
            layoutBottomBar.visibility = if (layoutBottomBar.isVisible) View.INVISIBLE else View.VISIBLE
        }
    }

}