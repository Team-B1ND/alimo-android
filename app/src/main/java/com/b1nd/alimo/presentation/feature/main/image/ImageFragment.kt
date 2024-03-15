package com.b1nd.alimo.presentation.feature.main.image

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.b1nd.alimo.R
import com.b1nd.alimo.data.model.FileModel
import com.b1nd.alimo.databinding.FragmentImageBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.main.MainActivity
import com.b1nd.alimo.presentation.feature.main.image.ImageViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.main.image.ImageViewModel.Companion.ON_CLICK_DOWNLOAD
import com.b1nd.alimo.presentation.feature.main.image.choose.ImageChooseDialogFragment
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.collectStateFlow
import com.b1nd.alimo.presentation.utiles.downloadFile
import com.b1nd.alimo.presentation.utiles.loadImage
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.shortToast
import com.b1nd.alimo.presentation.utiles.systemBarDark
import com.b1nd.alimo.presentation.utiles.toDateString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        initBar()
        initSideEffect()
        initNotification()

        Log.d("TAG", "initView: $notificationId $itemCount $itemIndex")

        bindingViewEvent {
            it.onSuccessEvent {
                when(it) {
                    ON_CLICK_BACK -> {
                        parentFragmentManager.popBackStack()
                    }
                    ON_CLICK_DOWNLOAD -> {
                        val model = viewModel.state.value
                        if (model != null) {
                            val items = model.images
                            val dialog = ImageChooseDialogFragment(
                                itemCount = itemCount,
                                onClickSaveAll = {
                                    Log.d("TAG", "initPager: saveAll")
                                    items.forEach { item ->
                                        downloadFile(
                                            url = item.fileUrl,
                                            name = item.fileName,
                                            extension = item.filetype ?: "*"
                                        )
                                    }
                                },
                                onClickSaveThat = {
                                    Log.d("TAG", "initPager: savaThat")
                                    val item = items[mBinding.pagerImage.currentItem]
                                    downloadFile(
                                        url = item.fileUrl,
                                        name = item.fileName,
                                        extension = item.filetype ?: "*"
                                    )
                                }
                            )
                            dialog.show(childFragmentManager, "image_choose_dialog")
                        }
                    }
                }
            }
        }
    }

    private fun initNotification() {
        viewModel.getNotificationImage(notificationId)
        collectStateFlow(viewModel.state) {
            mBinding.run {
                it?.run {
                    textAuthor.text = member
                    textDate.text = createdAt.toDateString()
                    if (memberProfile != null) {
                        imageProfile.loadImage(memberProfile)
                    }
                    initPager(items = images)
                }
            }
        }
    }

    private fun initSideEffect() {
        collectFlow(viewModel.sideEffect) {
            when(it) {
                is ImageSideEffect.FailedNotificationLoad -> {
                    requireContext().shortToast("로딩에 실패하였습니다.")
                }
            }
        }
    }

    private fun initBar() {
        mBinding.textCount.text = "${itemIndex+1}/${itemCount}"
    }

    private fun initPager(
        items: List<FileModel>
    ) {
        val adapter = ImageViewPagerAdapter(items){
            visibilityChangeLayout()
        }
        mBinding.pagerImage.adapter = adapter
        lifecycleScope.launch(Dispatchers.Main) {
            // delay를 안주면 페이지 설정이 안됨.
            delay(5)
            mBinding.pagerImage.setCurrentItem(itemIndex, false)
        }
        mBinding.pagerImage.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                lifecycleScope.launch(Dispatchers.Main) {
                    mBinding.textCount.text = "${position+1}/${itemCount}"
                }
            }
        })
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