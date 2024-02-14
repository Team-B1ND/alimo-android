package com.b1nd.alimo.presentation.feature.home

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentHomeBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.post.PostItem
import com.b1nd.alimo.presentation.feature.post.PostRecyclerAdapter
import com.b1nd.alimo.presentation.utiles.collectFlow
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
            this.add(HomeCategoryRvItem("...", false))
        }
        mBinding.rvCategory.adapter = HomeCategoryRv(testCategoryItem, requireContext()) {
        }
        mBinding.rvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        initNotice()


        lifecycleScope.launch(Dispatchers.Main) { // 가상 로딩 재현
            delay(1000)
            val testNotice= Pair("버그가 생겼어요!", "테스트")
            mBinding.textNotice.text = testNotice.first
            mBinding.textNoticeAuthor.text = " · ${testNotice.second}"
            mBinding.textNoticeAuthor.isVisible = true

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


//            adapter.submitData(PagingData.from(listOf(testData(2), testData(3), testData(4), testData(5), testData(6), testData(7), testData(8), testData(9), testData(10), testData(11), testData(12), testData(13), testData(14), testData(15), testData(16))))
//            Log.d("TAG", "initView: ${adapter.itemCount}")
        }
    }

    private fun initNotice() {
        val adapter = PostRecyclerAdapter() {
            Log.d("TAG", "initView: ${it.id}")

            val navigate =
                HomeFragmentDirections.actionNavItemHomeToDetailFragment(
                    it.id
                )
            findNavController().navigate(navigate)
        }

        mBinding.rvPost.adapter = adapter


        collectFlow(viewModel.pagingData) {
            adapter.submitData(lifecycle, it)
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