package com.b1nd.alimo.presentation.feature.main.detail

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.util.Patterns
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.b1nd.alimo.R
import com.b1nd.alimo.data.model.FileModel
import com.b1nd.alimo.databinding.FragmentDetailBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.custom.CustomEmoji
import com.b1nd.alimo.presentation.custom.CustomFileDownload
import com.b1nd.alimo.presentation.feature.main.MainActivity
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.BOOKMARK
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.NOT_BOOKMARK
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_ANGRY
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_BOOKMARK
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_LAUGH
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_LOVE
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_OKAY
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_SAD
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_SEND
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.getTimeString
import com.b1nd.alimo.presentation.utiles.loadImage
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: BaseFragment<FragmentDetailBinding, DetailViewModel>(R.layout.fragment_detail) {

    override val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()
    private var pickEmoji: CustomEmoji? = null
    override fun initView() {
        (requireActivity() as? MainActivity)?.bottomVisible(false)
//        addFiles(testFiles)
        initSideEffect()
        initNotice()
        initEmoji()

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
                    ON_CLICK_BOOKMARK -> {
//                        Log.d("TAG", "initView: ${mBinding.imageBookmark.tag.toString()}")
                        viewModel.pathBookmark(args.id)
                    }
                }
            }
        }
    }

    private fun initEmoji() {
        viewModel.loadEmoji(args.id)
        collectFlow(viewModel.emojiState) {
            mBinding.run {
                val emojis = mutableListOf(
                    imageOkay,
                    imageAngry,
                    imageLaugh,
                    imageLove,
                    imageSad
                )
                it.forEach { emoji ->
                    val index = getEmojiIndex(emoji.emojiName)
                    if (index != null) {
                        emojis[index].setCount(emoji.count.toString())
                    }
                }
            }
        }
    }


    private fun initSideEffect() {
        collectFlow(viewModel.sideEffect) {
            when(it) {
                is DetailSideEffect.FailedChangeEmoji -> {

                }
                is DetailSideEffect.FailedNotificationLoad -> {

                }
                is DetailSideEffect.FailedEmojiLoad -> {

                }
                is DetailSideEffect.SuccessChangeBookmark -> {
                    Log.d("TAG", "initSideEffect: ewqqwe")
                    changeBookmark()
                }
                is DetailSideEffect.FailedChangeBookmark -> {

                }
            }
        }
    }

    private fun initNotice() {
        viewModel.loadNotification(args.id)

        collectFlow(viewModel.notificationState) {
            mBinding.run {
                it?.let {
                    Log.d("TAG", "initNotice: ${it.emoji}")
                    textTitle.text = it.title
                    textAuthor.text = it.member
                    textContent.text = it.content
                    textDate.text = it.createdAt.toString()
                    if (it.memberProfile != null) {
                        imageProfile.loadImage(it.memberProfile)
                    }
                    if (it.images.isNotEmpty()) {
                        imageContent.isVisible = true
                        imageContent.loadImage(it.images[0].fileUrl)
                    }
                    if (it.files.isNotEmpty()) {
                        layoutFiles.isVisible = true
                        layoutFiles.removeAllViews()
                        addFiles(it.files)
                    }
                    if (it.isBookmark) {
                        imageBookmark.setImageResource(R.drawable.ic_bookmark)
                        imageBookmark.tag = BOOKMARK
                    }
                    // TODO(내가 반응한 이모지 표시하기)
                    val emojis = mutableListOf(
                        imageOkay,
                        imageAngry,
                        imageLaugh,
                        imageLove,
                        imageSad
                    )
                    if (it.emoji != null) {
                        getEmojiIndex(it.emoji)?.let { index ->
                            emojis[index].animAlpha(1f)
                            emojis.removeAt(index)
                        }
                        emojis.forEach { emoji ->
                            emoji.animAlpha(emojiAlpha)
                        }
                    }

                    val adapter = DetailCommentRv(it.comments) {
                        Log.d("TAG", "initView: $it")
                    }
                    mBinding.rvComment.adapter = adapter
                }
            }
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
            val emojiIndex = getEmojiIndex(emoji)
            viewModel.setEmoji(
                args.id,
                emoji.split("_").last()
            )

            emojis.getOrNull(emojiIndex?: 10)?.let { // 이모지 존재여부
                if (pickEmoji == it) { // 이모지 중복 클릭 처리
                    pickEmoji = null
                    emojis.forEach {
                        it.animAlpha(1f)
                    }
                    return@clickEmoji
                }
            }
            if (emojiIndex != null) {
                val allAlpha = emojis.sumOf {
                    (it.alpha*10).toInt()
                }
                Log.d("TAG", "clickEmoji: $allAlpha")
                val item = emojis.removeAt(emojiIndex)
                if (pickEmoji != null) {
                    pickEmoji!!.setCount(
                        (pickEmoji!!.count.toInt()-1).toString()
                    )
                }
                pickEmoji = item

                // 현재 모든 아이템이 선택되어있지 않던지, 아이템이 원래 선택이 안되있던가
                val plus = if (allAlpha == 50 || item.alpha == emojiAlpha) 1 else -1
                item.setCount((item.count.toInt()+plus).toString())
                item.animAlpha(1f)
                // 분기점 처리 지금 애가 선택된 애인가?
            }
            emojis.forEach {
                it.animAlpha(emojiAlpha)
            }
        }
    }
    private fun changeBookmark() {
        mBinding.run {
            val isBookmark = imageBookmark.tag.toString() == BOOKMARK
            imageBookmark.setImageResource(
                if (isBookmark) R.drawable.ic_not_bookmark
                else R.drawable.ic_bookmark
            )
            imageBookmark.tag = if (isBookmark) NOT_BOOKMARK else BOOKMARK

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
//`
//    private fun testData(id: Int, comments: Boolean = false) =
//        DetailCommentItem(
//            id = id,
//            "test",
//            "https://static.wikia.nocookie.net/iandyou/images/c/cc/IU_profile.jpeg/revision/latest?cb=20210730145437",
//            LocalDateTime.now(),
//            "testcontent\n알빠노\n라고할뻔",
//            if (comments) listOf(
//                testItem,
//                testItem,
//                testItem
//            ) else null
//        )`

    private fun addFiles(
        files: List<FileModel>
    ) {
        mBinding.layoutFiles.run {
            files.forEach {
                val view = CustomFileDownload(requireContext(), null)
                view.apply {
                    setFileName(it.fileName)
                    setFileSize(it.fileSize)
                    setFileLink(it.fileUrl)
                    setOnClickListener {
                        downloadFile(it)
                    }
                }
                addView(view)
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

    private fun getEmojiIndex(
        emoji: String
    ): Int? {
        var nowEmoji = emoji
        if (emoji.length < 6) {
            nowEmoji = "ON_CLICK_${emoji}"
        }
        Log.d("TAG", "getEmojiIndex: $nowEmoji")
        val emojiIndex = when (nowEmoji) {
            ON_CLICK_OKAY -> 0
            ON_CLICK_ANGRY -> 1
            ON_CLICK_LAUGH -> 2
            ON_CLICK_LOVE -> 3
            ON_CLICK_SAD -> 4
            else -> null
        }
        return emojiIndex
    }

    companion object {
        const val emojiAlpha = 0.3f
        val testFiles = listOf<Triple<String, String, String>>(
            Triple("테스트 파일", "3 KB", "https://i.pinimg.com/originals/71/03/b9/7103b9cb185aa84b96e7c3ad4e613080.jpg"),
            Triple("테스트 파일", "3 KB", "https://i.pinimg.com/originals/71/03/b9/7103b9cb185aa84b96e7c3ad4e613080.jpg"),
            Triple("테스트 파일", "3 KB", "https://i.pinimg.com/originals/71/03/b9/7103b9cb185aa84b96e7c3ad4e613080.jpg")
        )
//        val testItem = DetailCommentItem(
//            id = 3,
//            "test",
//            "https://static.wikia.nocookie.net/iandyou/images/c/cc/IU_profile.jpeg/revision/latest?cb=20210730145437",
//            LocalDateTime.now(),
//            "testcontent\n알빠노\n라고할뻔",
//            null)
    }

}