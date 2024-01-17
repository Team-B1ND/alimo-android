package com.b1nd.alimo.feature.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.b1nd.alimo.R
import com.b1nd.alimo.base.BaseFragment
import com.b1nd.alimo.databinding.FragmentHomeBinding
import com.b1nd.alimo.feature.post.PostItem
import com.b1nd.alimo.feature.post.PostRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()

    override fun initView() {
        val testCategoryItem = mutableListOf<HomeCategoryRvItem>().apply {
            this.add(HomeCategoryRvItem("전체", false))
            this.add(HomeCategoryRvItem("1학년", true))
            this.add(HomeCategoryRvItem("바인드", false))
            this.add(HomeCategoryRvItem("사운드체크", false))
            this.add(HomeCategoryRvItem("교장선생님이 알립니다", true))
            this.add(HomeCategoryRvItem("라고할뻔", false))

        }

        mBinding.rvCategory.adapter = HomeCategoryRv(testCategoryItem, requireContext()) {
        }
        mBinding.rvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val adapter = PostRecyclerAdapter() {
            Log.d("TAG", "initView: ${it.id}")

            val navigate = HomeFragmentDirections.actionNavItemHomeToDetailFragment(
                it.id
            )
            findNavController().navigate(navigate)
        }
        mBinding.rvPost.adapter = adapter
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)

//            adapter.submitData(PagingData.from(listOf(testData(0))))
//            delay(1000)
//            adapter.submitData(PagingData.from(listOf(testData(1))))
//            delay(1000)
//            adapter.submitData(PagingData.from(listOf(testData(2))))
//            delay(1000)
            adapter.submitData(PagingData.from(listOf(testData(2), testData(3), testData(4), testData(5), testData(6), testData(7), testData(8), testData(9), testData(10), testData(11), testData(12), testData(13), testData(14), testData(15), testData(16))))
//            delay(5000)
//            adapter.submitData(PagingData.from(listOf(testData(2))))
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
            false,
            false
        )


}