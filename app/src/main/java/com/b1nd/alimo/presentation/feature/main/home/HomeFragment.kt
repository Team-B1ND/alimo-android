package com.b1nd.alimo.presentation.feature.main.home

import android.os.Parcelable
import android.util.Log
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
    private var recyclerViewState: Parcelable? = null

    override fun initView() {
        initError()
        initNotice()
        initSpeaker()
        initCategory()

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
    }

    private fun initSpeaker() {
        viewModel.loadSpeaker()

        collectFlow(viewModel.speakerData) {
            if (it == null) {
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
                        delay(500)
//                        adapter.retry()
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
        mBinding.rvCategory.adapter = HomeCategoryRv(testCategoryItem, requireContext()) {
            viewModel.setCategory(it.category)
        }
        mBinding.rvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        collectFlow(viewModel.categoryData) {
            val category = it.toMutableList()
            category.add(0, HomeCategoryRvItem("전체", false))
            mBinding.rvCategory.adapter = HomeCategoryRv(category, requireContext()) {
                // TODO(현재 게시글 초기화 -> 재로딩)
                mBinding.rvPost.scrollToPosition(0);
                adapter.submitData(lifecycle, PagingData.empty())
                viewModel.setCategory(it.category)
            }
        }
    }



}