package com.b1nd.alimo.presentation.feature.main.home

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentHomeBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.main.post.PostRecyclerAdapter
import com.b1nd.alimo.presentation.utiles.collectFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModels()

    override fun initView() {
        initError()
        initNotice()
        initCategory()

        lifecycleScope.launch(Dispatchers.Main) { // 가상 로딩 재현
            delay(1000)



//            adapter.submitData(PagingData.from(listOf(testData(2), testData(3), testData(4), testData(5), testData(6), testData(7), testData(8), testData(9), testData(10), testData(11), testData(12), testData(13), testData(14), testData(15), testData(16))))
//            Log.d("TAG", "initView: ${adapter.itemCount}")
        }
    }

    private fun initError() {
        collectFlow(viewModel.sideEffect) {
            when (it) {
                is HomeSideEffect.NotFound -> {
                    when (it.found) {
                        is HomeFound.Category -> {

                        }

                        HomeFound.Notice -> {
                            lifecycleScope.launch(Dispatchers.Main) {
                                val testNotice= Pair("버그가 생겼어요!", "테스트")
                                mBinding.textNotice.text = testNotice.first
                                mBinding.textNoticeAuthor.text = " · ${testNotice.second}"
                                mBinding.textNoticeAuthor.isVisible = true
                            }
                        }
                        HomeFound.Post -> {}
                    }
                }
            }
        }
    }


    private fun initNotice() {
        val adapter = PostRecyclerAdapter() {
            Log.d("TAG", "initView: ${it.notificationId}")

            val navigate =
                HomeFragmentDirections.actionNavItemHomeToDetailFragment(
                    it.notificationId
                )
            findNavController().navigate(navigate)
        }

        mBinding.rvPost.adapter = adapter


        collectFlow(viewModel.pagingData) {
            adapter.submitData(lifecycle, it)
        }

    }

    private fun initCategory() {
        val testCategoryItem = mutableListOf<HomeCategoryRvItem>().apply {
            this.add(HomeCategoryRvItem("전체", false))
            this.add(HomeCategoryRvItem("...", false))
        }
        mBinding.rvCategory.adapter = HomeCategoryRv(testCategoryItem, requireContext()) {

        }
        mBinding.rvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        collectFlow(viewModel.categoryData) {
            val category = it.toMutableList()
            category.add(0, HomeCategoryRvItem("전체", false))
            mBinding.rvCategory.adapter = HomeCategoryRv(category, requireContext()) {
                // TODO(현재 게시글 초기화 -> 재로딩)
            }
        }
    }


}