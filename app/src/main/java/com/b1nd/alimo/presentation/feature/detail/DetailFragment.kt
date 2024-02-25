package com.b1nd.alimo.presentation.feature.detail

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentDetailBinding
import com.b1nd.alimo.presentation.MainActivity
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.custom.CustomEmoji
import com.b1nd.alimo.presentation.custom.CustomFileDownload
import com.b1nd.alimo.presentation.feature.detail.DetailViewModel.Companion.ON_CLICK_ANGRY
import com.b1nd.alimo.presentation.feature.detail.DetailViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.detail.DetailViewModel.Companion.ON_CLICK_LAUGH
import com.b1nd.alimo.presentation.feature.detail.DetailViewModel.Companion.ON_CLICK_LOVE
import com.b1nd.alimo.presentation.feature.detail.DetailViewModel.Companion.ON_CLICK_OKAY
import com.b1nd.alimo.presentation.feature.detail.DetailViewModel.Companion.ON_CLICK_SAD
import com.b1nd.alimo.presentation.feature.detail.DetailViewModel.Companion.ON_CLICK_SEND
import com.b1nd.alimo.presentation.utiles.getTimeString
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class DetailFragment: BaseFragment<FragmentDetailBinding, DetailViewModel>(R.layout.fragment_detail) {

    override val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()

    override fun initView() {
        (requireActivity() as? MainActivity)?.bottomVisible(false)
        addFiles(testFiles)
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when(it) {
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }
                    ON_CLICK_SEND -> {
                        Log.d("TAG", "initView: send")
                        with(mBinding) {
                            val text = editSend.text.toString()
                            if (text.isNullOrBlank()) {
                                return@onSuccessEvent
                            }
                            editSend.text.clear()

                        }
                    }
                    ON_CLICK_OKAY -> {
                        clickEmoji(it)
                    }
                    ON_CLICK_ANGRY -> {
                        clickEmoji(it)
                    }
                    ON_CLICK_LAUGH -> {
                        clickEmoji(it)
                    }
                    ON_CLICK_LOVE -> {
                        clickEmoji(it)
                    }
                    ON_CLICK_SAD -> {
                        clickEmoji(it)
                    }
                }
            }
        }

        val adapter = DetailCommentRv {
            Log.d("TAG", "initView: $it")
        }
        mBinding.rvComment.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            val testData = PagingData.from(listOf(testData(0), testData(1, true), testData(2), ))
            adapter.submitData(testData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as? MainActivity)?.bottomVisible(true)
    }

    private fun clickEmoji(
        emoji: String
    ) {
        with(mBinding) {
            val emojis = mutableListOf(
                imageOkay,
                imageAngry,
                imageLaugh,
                imageLove,
                imageSad
            )
            val emojiIndex = when (emoji) {
                ON_CLICK_OKAY -> 0
                ON_CLICK_ANGRY -> 1
                ON_CLICK_LAUGH -> 2
                ON_CLICK_LOVE -> 3
                ON_CLICK_SAD -> 4
                else -> null
            }
            if (emojiIndex != null) {
                emojis.removeAt(emojiIndex)
                    .animAlpha(1f)
            }
            emojis.forEach {
                it.animAlpha(emojiAlpha)
            }
        }
    }

    private fun CustomEmoji.animAlpha(alpha: Float) {
        if (this.alpha == emojiAlpha && alpha == emojiAlpha ) {
            return
        } else {
            this.animate()
                .alpha(alpha)
                .setDuration(200)
        }
    }

    private fun testData(id: Int, comments: Boolean = false) =
        DetailCommentItem(
            id = id,
            "test",
            "https://static.wikia.nocookie.net/iandyou/images/c/cc/IU_profile.jpeg/revision/latest?cb=20210730145437",
            LocalDateTime.now(),
            "testcontent\n알빠노\n라고할뻔",
            if (comments) listOf(
                testItem,
                testItem,
                testItem
            ) else null
        )

    private fun addFiles(
        testFiles: List<Triple<String, String, String>>
    ) {
        mBinding.layoutFiles.run {
            testFiles.forEach {
                val view = CustomFileDownload(requireContext(), null)
                addView(view)
                view.apply {
                    setFileName(it.first)
                    setFileSize(it.second)
                    setFileLink(it.third)
                    setOnClickListener {
                        downloadFile(it)
                    }
                }
            }
        }
    }

    private fun downloadFile(
        url: String
    ) {
        if (!Patterns.WEB_URL.matcher(url).matches()) { // 웹 url인지 유효성 검사
            return
        }
        val extension = url.split(".").last()
        Log.d("TAG", "downloadFile: $extension")
        val fileName = "${getString(R.string.app_name)}/${getTimeString(extension)}"

        val mimeType = when(extension) {
            "jpg", "png", "jpeg", "gif", "bmp", "webp" -> "image/*"
            "plane", "html", "css", "javascript", "txt", "json" -> "text/*"
            "midi", "mpeg", "wav", "mp3" -> "audio/*"
            "webm", "mp4", "ogg", "avi", "mkv", "flv", "m4p", "m4v" -> "video/*"
            else -> "application/${extension}"
        }
        Log.d("TAG", "downloadFile: $mimeType")


        val downloadManager = DownloadManager.Request(Uri.parse(url))

        downloadManager.setTitle(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // 알림 설정
            .setMimeType(mimeType)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                fileName
            ) // 다운로드 완료 시 보여지는 이름

        val manager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(downloadManager)
    }

    companion object {
        const val emojiAlpha = 0.3f
        val testFiles = listOf<Triple<String, String, String>>(
            Triple("테스트 파일", "3 KB", "https://i.pinimg.com/originals/71/03/b9/7103b9cb185aa84b96e7c3ad4e613080.jpg"),
            Triple("테스트 파일", "3 KB", "https://i.pinimg.com/originals/71/03/b9/7103b9cb185aa84b96e7c3ad4e613080.jpg"),
            Triple("테스트 파일", "3 KB", "https://i.pinimg.com/originals/71/03/b9/7103b9cb185aa84b96e7c3ad4e613080.jpg")
        )
        val testItem = DetailCommentItem(
            id = 3,
            "test",
            "https://static.wikia.nocookie.net/iandyou/images/c/cc/IU_profile.jpeg/revision/latest?cb=20210730145437",
            LocalDateTime.now(),
            "testcontent\n알빠노\n라고할뻔",
            null)
    }

}