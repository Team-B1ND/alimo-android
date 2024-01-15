package com.b1nd.alimo.feature.bookmark

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentBookmarkBinding
import com.b1nd.alimo.feature.post.PostItem
import com.b1nd.alimo.feature.post.PostRecyclerAdapter
import com.b1nd.alimo.utiles.startAnimationWithHide
import com.b1nd.alimo.utiles.startAnimationWithShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class BookmarkFragment: BaseFragment<FragmentBookmarkBinding, BookmarkViewModel>(R.layout.fragment_bookmark) {

    override val viewModel: BookmarkViewModel by viewModels()

    override fun initView() {
        val adapter = PostRecyclerAdapter {
            val navigate = BookmarkFragmentDirections.actionNavItemBookmarkToDetailFragment(
                it.id
            )
            findNavController().navigate(navigate)
        }
        mBinding.rvPost.visibility = View.GONE
        mBinding.layoutNot.visibility = View.VISIBLE
        adapter.addLoadStateListener { combinedLoadStates ->
//            mBinding.layoutNot.isVisible = combinedLoadStates.source.refresh is LoadState.Loading || combinedLoadStates.source.refresh is LoadState.Error
            Log.d("TAG", "initView: ${adapter.itemCount}")
//            mBinding.rvPost.isVisible = adapter.itemCount != 0 && combinedLoadStates.source.refresh is LoadState.NotLoading
//            mBinding.layoutNot.isVisible = adapter.itemCount == 0
//            mBinding.rvPost.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading


        }
        mBinding.rvPost.adapter = adapter

        lifecycleScope.launch(Dispatchers.Main) {
            delay(1000)
            adapter.submitData(PagingData.from(listOf(testData(2), testData(3), testData(4), testData(5), testData(6), testData(7), testData(8), testData(9), testData(10), testData(11), testData(12), testData(13), testData(14), testData(15), testData(16))))

            if (adapter.itemCount != 0) {
                startAnimationWithHide(mBinding.layoutNot, R.anim.exit)
                startAnimationWithShow(mBinding.rvPost, R.anim.enter)
            } else {
                startAnimationWithHide(mBinding.rvPost, R.anim.exit)
                startAnimationWithShow(mBinding.layoutNot, R.anim.enter)
            }
        }
    }

    private fun testData(id: Int): PostItem =
        PostItem(
            id,
            "test",
            "https://static.wikia.nocookie.net/iandyou/images/c/cc/IU_profile.jpeg/revision/latest?cb=20210730145437",
            LocalDateTime.now(),
            "testcontent\n알빠노\n라고할뻔",
            "https://static.wikia.nocookie.net/iandyou/images/c/cc/IU_profile.jpeg/revision/latest?cb=20210730145437",
            true,
            false
        )

}