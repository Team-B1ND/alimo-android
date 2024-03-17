package com.b1nd.alimo.presentation.feature.main.home

import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentHomeBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.main.home.HomeViewModel.Companion.ON_CLICK_SPEAKER
import com.b1nd.alimo.presentation.feature.main.post.PostRecyclerAdapter
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.shortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: PostRecyclerAdapter
    private lateinit var categoryAdapter: HomeCategoryRv
    private var recyclerViewState: Parcelable? = null

    override fun initView() {
        initError()
        initNotice()
        initSpeaker()
        initCategory()
        initRefresh()

        bindingViewEvent {
            it.onSuccessEvent {
                when(it) {
                    ON_CLICK_SPEAKER -> {
                        viewModel.speakerData.value?.run {
                            val navigate = HomeFragmentDirections
                                .actionNavItemHomeToDetailFragment(notificationId)
                            findNavController().navigate(navigate)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadMyCategory()
    }

    override fun onPause() {
        super.onPause()
        // 스크롤 상태 저장
        recyclerViewState = mBinding.rvPost.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        // 스크롤 상태 복원
        recyclerViewState?.let { state ->
            mBinding.rvPost.layoutManager?.onRestoreInstanceState(state)
        }

        initNowCategory()
    }

    private fun initNowCategory() {
        // 현재 선택된 카테고리 뷰 상태에 반영
        lifecycleScope.launch(Dispatchers.Main) {
            // 실제 rv 반영 속도에 따른 딜레이
            delay(50)
            val targetCategoryViewHolder = (mBinding.rvCategory.findViewHolderForAdapterPosition(viewModel.chooseCategory.value.second)
                    as? HomeCategoryRv.ViewHolder)
            val categoryFirstViewHolder = (mBinding.rvCategory.findViewHolderForAdapterPosition(0)
                    as? HomeCategoryRv.ViewHolder)
            Log.d("TAG", "onResume: $categoryFirstViewHolder")
            targetCategoryViewHolder?.binding?.run {
                categoryFirstViewHolder?.binding?.layoutCategory?.setBackgroundResource(R.drawable.ripple_gray100_12)
                categoryFirstViewHolder?.binding?.text?.setTextColor(requireContext().getColor(R.color.Gray500))
                categoryAdapter.setNowChooseItem(targetCategoryViewHolder)

                layoutCategory.setBackgroundResource(R.drawable.ripple_main500_12)
                text.setTextColor(requireContext().getColor(R.color.Main900))
                imageBadge.visibility = View.GONE

            }
        }
    }

    private fun initRefresh() {
        mBinding.layoutAppbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            mBinding.layoutSwipeRefresh.isEnabled = verticalOffset == 0
        }
        mBinding.layoutSwipeRefresh.setOnRefreshListener {
            adapter.refresh()
            mBinding.layoutSwipeRefresh.isRefreshing = false
        }
    }

    private fun initSpeaker() {
        collectFlow(viewModel.speakerData) {
            if (it == null) {
                Log.d("TAG", "initSpeaker: ")
                lifecycleScope.launch(Dispatchers.Main) {
                    mBinding.textSpeaker.text = "등록된 공지가 없습니다."
                }
                return@collectFlow
            }
            lifecycleScope.launch(Dispatchers.Main) {
                mBinding.run {
                    textSpeaker.text = it.title
                    textSpeakerAuthor.text = " ·  ${it.member}"
                    textSpeakerAuthor.isVisible = true
                }
            }
        }
        viewModel.loadSpeaker()
    }

    private fun initError() {
        collectFlow(viewModel.sideEffect) {
            when (it) {
                is HomeSideEffect.NotFound -> {
                    when (it.found) {
                        is HomeFound.Category -> {
                            lifecycleScope.launch(Dispatchers.Main) {
                                delay(500)
                                viewModel.loadMyCategory()
                            }
                        }

                        HomeFound.Speaker -> {
                            lifecycleScope.launch(Dispatchers.Main) {
                                val testNotice= Pair("버그가 생겼어요!", "테스트")
                                mBinding.textSpeaker.text = testNotice.first
                                mBinding.textSpeakerAuthor.text = " · ${testNotice.second}"
                                mBinding.textSpeakerAuthor.isVisible = true
                            }
                        }
                        HomeFound.Post -> {}
                    }
                }
                is HomeSideEffect.NetworkError -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        requireContext().shortToast(it.message)
                    }
                }
                is HomeSideEffect.FailedChangeEmoji -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        requireContext().shortToast("이모지를 설정하는데 실패하였습니다.")
                    }
                }
                is HomeSideEffect.FailedChangeBookmark -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        requireContext().shortToast("북마크를 설정하는데 실패하였습니다.")
                    }
                }
            }
        }
    }


    private fun initNotice() {
        adapter = PostRecyclerAdapter(
            context = requireContext(),
            onClickBookmark = { notificationId ->
                viewModel.patchBookmark(notificationId)
            },
            onClickEmoji = { notificationId, emoji ->
                viewModel.setEmoji(
                    notificationId = notificationId,
                    emoji = emoji
                )
            },
            onClick = {
                Log.d("TAG", "initView: ${it.notificationId}")

                val navigate =
                    HomeFragmentDirections.actionNavItemHomeToDetailFragment(
                        it.notificationId
                    )
                findNavController().navigate(navigate)
            }
        )

        mBinding.rvPost.adapter = adapter


        collectFlow(viewModel.pagingData) {
            adapter.submitData(lifecycle, it)
        }


        adapter.addLoadStateListener { loadState ->
            when(loadState.refresh) {
                is LoadState.Error -> {
                    // 에러 상태일 때 처리
                    val errorState = loadState.refresh as LoadState.Error
                    Log.d("TAG", "initNotice: ${errorState.error.message}")
                    viewModel.addErrorCount()
                    lifecycleScope.launch(Dispatchers.Main) {
                        delay(1000)
                        adapter.retry()
                    }
                }
                is LoadState.NotLoading -> {
                    // 초기 로드 또는 리프레시가 완료되었을 때 원하는 작업 수행
                    lifecycleScope.launch(Dispatchers.Main) {
                        if (mBinding.rvPost.computeVerticalScrollOffset() > 0) {
                            mBinding.layoutAppbar.setExpanded(false, true)
                        }
                    }
                }

                else -> {}
            }

        }

    }

    private fun initCategory() {
        val testCategoryItem = mutableListOf<HomeCategoryRvItem>().apply {
            this.add(HomeCategoryRvItem("전체", false))
            this.add(HomeCategoryRvItem("...", false))
        }
        categoryAdapter = HomeCategoryRv(testCategoryItem, requireContext()) { item, positon ->
            viewModel.setCategory(item.category, positon)
        }
        mBinding.rvCategory.adapter = categoryAdapter
        mBinding.rvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        collectFlow(viewModel.categoryData) {
            val category = it.toMutableList()
            category.add(0, HomeCategoryRvItem("전체", false))
            categoryAdapter = HomeCategoryRv(category, requireContext()) { item, positon ->
                // TODO(현재 게시글 초기화 -> 재로딩)
                mBinding.rvPost.scrollToPosition(0);
                adapter.submitData(lifecycle, PagingData.empty())
                viewModel.setCategory(item.category, positon)
            }

            mBinding.rvCategory.adapter = categoryAdapter
        }
    }



}