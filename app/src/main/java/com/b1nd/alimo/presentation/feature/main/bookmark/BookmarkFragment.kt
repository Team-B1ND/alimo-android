package com.b1nd.alimo.presentation.feature.main.bookmark

import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.b1nd.alimo.R
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.databinding.FragmentBookmarkBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.main.post.PostRecyclerAdapter
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.shortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class BookmarkFragment: BaseFragment<FragmentBookmarkBinding, BookmarkViewModel>(R.layout.fragment_bookmark) {

    override val viewModel: BookmarkViewModel by viewModels()
    private lateinit var adapter: PostRecyclerAdapter
    private var recyclerViewState: Parcelable? = null

    override fun initView() {
        initNotification()
        initSideEffect()
//        mBinding.rvPost.visibility = View.GONE
//        mBinding.layoutNot.visibility = View.VISIBLE
//        adapter.addLoadStateListener { combinedLoadStates ->
////            mBinding.layoutNot.isVisible = combinedLoadStates.source.refresh is LoadState.Loading || combinedLoadStates.source.refresh is LoadState.Error
//            Log.d("TAG", "initView: ${adapter.itemCount}")
////            mBinding.rvPost.isVisible = adapter.itemCount != 0 && combinedLoadStates.source.refresh is LoadState.NotLoading
////            mBinding.layoutNot.isVisible = adapter.itemCount == 0
////            mBinding.rvPost.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading
//
//
//        }
//
//        lifecycleScope.launch(Dispatchers.Main) {
//            delay(1000)
//            adapter.submitData(PagingData.from(listOf(testData(2), testData(3), testData(4), testData(5), testData(6), testData(7), testData(8), testData(9), testData(10), testData(11), testData(12), testData(13), testData(14), testData(15), testData(16))))
//
//            if (adapter.itemCount != 0) {
//                startAnimationWithHide(mBinding.layoutNot, R.anim.exit)
//                startAnimationWithShow(mBinding.rvPost, R.anim.enter)
//            } else {
//                startAnimationWithHide(mBinding.rvPost, R.anim.exit)
//                startAnimationWithShow(mBinding.layoutNot, R.anim.enter)
//            }
//        }
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
    }

    private fun initSideEffect() {
        collectFlow(viewModel.sideEffect) {
            when(it) {
                is BookmarkSideEffect.NetworkError -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        requireContext().shortToast("네트워크가 연결되지 않았습니다.")
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
                viewModel.patchBookmark(notificationId)
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

    private fun testData(id: Int): NotificationModel =
        NotificationModel(
            id,
            "test",
            0,
            "member",
            "https://static.wikia.nocookie.net/iandyou/images/c/cc/IU_profile.jpeg/revision/latest?cb=20210730145437",
            createdAt = LocalDateTime.now(),
            "testcontent\n알빠노\n라고할뻔",
            false,
            "https://static.wikia.nocookie.net/iandyou/images/c/cc/IU_profile.jpeg/revision/latest?cb=20210730145437",
            isBookmark = false,
            isNew = true,
            emoji = "OKAY",
            images = emptyList(),
            files = emptyList()
        )

}