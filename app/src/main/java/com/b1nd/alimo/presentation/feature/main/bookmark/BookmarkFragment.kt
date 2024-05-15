package com.b1nd.alimo.presentation.feature.main.bookmark

import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentBookmarkBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.main.post.PostRecyclerAdapter
import com.b1nd.alimo.presentation.utiles.Dlog
import com.b1nd.alimo.presentation.utiles.Env
import com.b1nd.alimo.presentation.utiles.Env.AUTO_REFRESH_NOTIFICATION
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.shortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkFragment: BaseFragment<FragmentBookmarkBinding, BookmarkViewModel>(R.layout.fragment_bookmark) {

    override val viewModel: BookmarkViewModel by viewModels()
    private lateinit var adapter: PostRecyclerAdapter
    private var recyclerViewState: Parcelable? = null

    override fun initView() {
        initNotification()
        initSideEffect()
        initRefresh()
    }

    private fun initRefresh() {
        mBinding.layoutSwipeRefresh.setOnRefreshListener {
            adapter.refresh()
            mBinding.layoutSwipeRefresh.isRefreshing = false
        }
    }

    override fun onPause() {
        super.onPause()
        // 스크롤 상태 저장
        recyclerViewState = mBinding.rvPost.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        // 스크롤 상태 복원
        adapter.refresh()
        recyclerViewState?.let { state ->
            mBinding.rvPost.layoutManager?.onRestoreInstanceState(state)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            adapter.onPagesUpdatedFlow.collectLatest {
                mBinding.layoutNot.visibility = if (adapter.itemCount <= 0 && mBinding.layoutNot.isVisible) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initSideEffect() {
        collectFlow(viewModel.sideEffect) {
            when(it) {
                is BookmarkSideEffect.NetworkError -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        requireContext().shortToast(Env.NETWORK_ERROR_MESSAGE)
                    }
                }
                is BookmarkSideEffect.FailedChangeBookmark -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        requireContext().shortToast("북마크를 설정하는데 실패하였습니다.")
                    }
                }
                is BookmarkSideEffect.FailedChangeEmoji -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        requireContext().shortToast("이모지를 설정하는데 실패하였습니다.")
                    }
                }
            }
        }
    }

    private fun initNotification() {
        adapter = PostRecyclerAdapter(
            requireContext(),
            onClickBookmark = { notificationId ->
                Dlog.d("initNotification: 북마크 클릭함")
                viewModel.patchBookmark(notificationId)
                lifecycleScope.launch(Dispatchers.Main) {
                    delay(200)
                    adapter.refresh()
                }
            },
            onClickEmoji = { notificationId, emoji ->
                viewModel.setEmoji(
                    notificationId = notificationId,
                    emoji = emoji
                )
            },
            onClick = {
                val navigate =
                    BookmarkFragmentDirections.actionNavItemBookmarkToDetailFragment(
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
                    Dlog.d("initNotice: ${errorState.error.message}")
                    viewModel.addErrorCount()
                    lifecycleScope.launch(Dispatchers.Main) {
                        delay(AUTO_REFRESH_NOTIFICATION)
                        adapter.retry()
                    }
                }
                else -> {}
            }

        }
    }
}