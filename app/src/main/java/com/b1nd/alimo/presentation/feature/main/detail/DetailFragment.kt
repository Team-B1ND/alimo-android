package com.b1nd.alimo.presentation.feature.main.detail

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.allViews
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.CHOOSE
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.NOT_BOOKMARK
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.NOT_CHOOSE
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_ANGRY
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_BOOKMARK
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_LAUGH
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_LOVE
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_OKAY
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_SAD
import com.b1nd.alimo.presentation.feature.main.detail.DetailViewModel.Companion.ON_CLICK_SEND
import com.b1nd.alimo.presentation.feature.main.detail.delete.CommentDeleteDialog
import com.b1nd.alimo.presentation.feature.main.image.ImageFragment
import com.b1nd.alimo.presentation.utiles.Dlog
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.collectStateFlow
import com.b1nd.alimo.presentation.utiles.downloadFile
import com.b1nd.alimo.presentation.utiles.getResourceString
import com.b1nd.alimo.presentation.utiles.loadImage
import com.b1nd.alimo.presentation.utiles.loadNotCropImage
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.repeatOnStarted
import com.b1nd.alimo.presentation.utiles.systemBarDark
import com.b1nd.alimo.presentation.utiles.toDateString
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment: BaseFragment<FragmentDetailBinding, DetailViewModel>(R.layout.fragment_detail) {

    override val viewModel: DetailViewModel by viewModels()

    private val args: DetailFragmentArgs by navArgs()
    private var pickEmoji: CustomEmoji? = null
    // 이모지 새 로드시 True로 전환
    private var isLoadEmoji: Boolean = false

    private var parentId: Int? = null

    override fun initView() {
        initSideEffect()
        initTouch()
        initEmoji()
        initNotice()
        initBackHandler()
        viewModel.loadProfile()

        bindingViewEvent { event ->
            event.onSuccessEvent {
                when(it) {
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }
                    ON_CLICK_SEND -> {
                        requestSendText()
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
                        viewModel.pathBookmark(args.id)
                    }
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        (requireActivity() as? MainActivity)?.bottomVisible(false)
        changeVisibleAnimationView(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        for (i in mBinding.layoutImageContent.allViews) {
            Glide.with(requireContext()).clear(i)
        }
    }

    private fun changeVisibleAnimationView(visible: Boolean) {
        mBinding.imageAnim.isVisible = visible
        if (visible) {
            mBinding.imageAnim.run {
                alpha = 0.6f
                animate()
                    .setInterpolator(DecelerateInterpolator())
                    .alpha(1f)
                    .setDuration(150)
                    .setListener(object: Animator.AnimatorListener {
                        override fun onAnimationStart(p0: Animator) {
                        }

                        override fun onAnimationEnd(p0: Animator) {
                            lifecycleScope.launch(Dispatchers.Main) {
                                delay(50)
                                alpha = 0.9f
                                isVisible = false
                            }
                        }

                        override fun onAnimationCancel(p0: Animator) {
                        }

                        override fun onAnimationRepeat(p0: Animator) {

                        }
                    })

            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouch() {
        mBinding.layoutParent.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                clearFocus()
            }

            return@setOnTouchListener true
        }
        mBinding.rvComment.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                clearFocus()
            }

            return@setOnTouchListener true
        }
    }

    private fun clearFocus() {
        mBinding.run {
            // 답글 달기 취소
            // 고려요소, 현재 댓글이 전송중인지
            // 에딧텍스트 포커스 해제
            editSend.clearFocus()
            val manager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(
                editSend.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            if (editSend.isEnabled) {
                parentId = null
                textParentCommenter.visibility = View.GONE
            }
        }
    }

    private fun requestSendText() {
        Dlog.d("initView: send")
        with(mBinding) {
            if (!editSend.isEnabled) {
                return
            }

            val text = editSend.text.toString()
            if (text.isBlank()) {
                return
            }
            editSend.isEnabled = false
            viewModel.postSend(
                notificationId = args.id,
                text = text,
                commentId = parentId
            )
        }
    }

    private fun initEmoji() {
        viewModel.loadEmoji(args.id)

        collectStateFlow(viewModel.emojiState) {
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
                    emojis[index].setCount(emoji.count.toString())
                }
            }
        }

        repeatOnStarted {
            var nowItem = pickEmoji
            var beforeItem: CustomEmoji? = null
            while (true) {
                if (isLoadEmoji) {
                    nowItem = pickEmoji
                    isLoadEmoji = false
                    delay(1000)
                    continue
                }
                if (nowItem != pickEmoji) {
                    nowItem = pickEmoji
                    if (pickEmoji?.tag == CHOOSE) {
                        // 이모지 등록 처리
                        viewModel.setEmoji(
                            args.id,
                            pickEmoji!!.emojiName
                        )
                    } else {
                        // 이모지 취소 처리
                        viewModel.setEmoji(
                            args.id,
                            beforeItem!!.emojiName
                        )
                    }
                }
                beforeItem = nowItem
                delay(1000)
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
                    Dlog.d("initSideEffect: ewqqwe")
                    changeBookmark()
                }
                is DetailSideEffect.FailedChangeBookmark -> {

                }
                is DetailSideEffect.FailedPostComment -> {

                }
                is DetailSideEffect.SuccessAddComment -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        mBinding.editSend.text.clear()
                        mBinding.editSend.isEnabled = true
                    }
                }
                is DetailSideEffect.SuccessDeleteComment -> {

                }
                is DetailSideEffect.FailedLoadProfile -> {

                }

                is DetailSideEffect.FailedDeleteComment -> {}
            }
        }
    }

    private fun initNotice() {
        viewModel.loadNotification(args.id)

        collectStateFlow(viewModel.state) { state ->
            mBinding.run {
                parentId = null
                textParentCommenter.visibility = View.GONE
                state.notificationState?.let {
                    Dlog.d("initNotice: ${it.emoji}")
                    textTitle.text = it.title
                    textAuthor.text = it.member
                    textContent.text = it.content
                    textDate.text = it.createdAt.toDateString()
                    if (it.memberProfile != null) {
                        imageProfile.loadImage(it.memberProfile)
                    }
                    val scroll = Pair(layoutNestedScroll.scrollX, layoutNestedScroll.scrollY)

                    if (it.images.isNotEmpty()) {
                        layoutImageContent.isVisible = true
                        layoutImageContent.removeAllViews()
                    }
                    it.images.forEachIndexed { index, file ->
                        val imageView = ImageView(requireContext())
                        imageView.run {
                            maxWidth = 30
                            background = AppCompatResources.getDrawable(requireContext(), R.drawable.shape_image_view)
                            clipToOutline = true
                            isVisible = true
                            setOnClickListener { _ ->
                                systemBarDark(true)
                                changeVisibleAnimationView(true)
                                mBinding.fragmentImage.isVisible = true
                                fun addMapFragment() {
                                    childFragmentManager.beginTransaction().apply {
                                        setCustomAnimations(
                                            R.anim.image_enter,
                                            R.anim.exit,
                                            R.anim.enter,
                                            R.anim.exit
                                        )
                                        val imageFragment = ImageFragment(
                                            notificationId = args.id,
                                            itemCount = it.images.size,
                                            itemIndex = index
                                        )
                                        replace(R.id.fragment_image, imageFragment)
                                        setReorderingAllowed(true)
                                        addToBackStack(null)
                                        commit()
                                    }

                                }
                                addMapFragment()
                            }
                        }

                        imageView.loadNotCropImage(file.fileUrl) { ratio ->
                            Dlog.d("initNotice: $ratio")
                            lifecycleScope.launch(Dispatchers.Main) {
                                layoutNestedScroll.post {
                                    layoutNestedScroll.scrollTo(scroll.first, scroll.second)
                                }
                                val constSet = ConstraintSet()
                                constSet.clone(layoutParent)
                                constSet.setDimensionRatio(imageContent.id, "H,${ratio}")
                                constSet.applyTo(layoutParent)
                            }
                        }
                        layoutImageContent.addView(imageView)

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
                        val index = getEmojiIndex(it.emoji)
                        val item = emojis.removeAt(index)
                        item.animAlpha(1f)
                        item.tag = CHOOSE
                        pickEmoji = item
                        emojis.forEach { emoji ->
                            emoji.animAlpha(EMOJI_ALPHA)
                        }
                        isLoadEmoji = true
                    }

                    val adapter = DetailCommentRv(
                        items = it.comments,
                        userId = viewModel.profileData.value?.memberId,
                        onClickReply = {
                            val commenter = it.commenter + getResourceString(R.string.comment_item_that)
                            parentId = it.commentId
                            textParentCommenter.visibility = View.VISIBLE
                            textParentCommenter.text = commenter
                        },
                        onLongClickComment = { commentId, isSub ->
                            CommentDeleteDialog(
                                onClickDelete = {
                                    viewModel.deleteComment(
                                        notificationId = args.id,
                                        commentId = commentId,
                                        isSub = isSub
                                    )
                                }
                            ).show(childFragmentManager, "commentDeleteDialog`")
                        }
                    )
                    mBinding.rvComment.adapter = adapter
                }
            }
        }

        collectStateFlow(viewModel.profileData) { data ->
            if (data == null) { return@collectStateFlow }
            if (viewModel.state.value.notificationState == null) { return@collectStateFlow }
            with(viewModel.state.value.notificationState!!) {
                val adapter = DetailCommentRv(
                    items = comments,
                    userId = data.memberId,
                    onClickReply = {
                        val commenter = it.commenter + getResourceString(R.string.comment_item_that)
                        parentId = it.commentId
                        mBinding.textParentCommenter.visibility = View.VISIBLE
                        mBinding.textParentCommenter.text = commenter
                    },
                    onLongClickComment = { commentId, isSub ->
                        CommentDeleteDialog(
                            onClickDelete = {
                                viewModel.deleteComment(
                                    notificationId = args.id,
                                    commentId = commentId,
                                    isSub = isSub
                                )
                            }
                        ).show(childFragmentManager, "commentDeleteDialog`")
                    }
                )
                mBinding.rvComment.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as? MainActivity)?.bottomVisible(true)
    }

    private fun initBackHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (parentId != null) {
                    parentId = null
                    mBinding.textParentCommenter.visibility = View.GONE
                    return
                }
                findNavController().popBackStack()
            }

        })
    }

    private fun clickEmoji(
        emoji: String,
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

            // 이모지 클릭시 나머지 이모지 알파 처리
            // 클릭된 이모지 +1 처리
            // 원래 클릭되있던 이모지 -1 처리

            // 원래 클릭되있던 이모지를 클릭할 경우 모두 알파 1f 처리
            // 원래 클릭된 이모지 +1 처리

            val nowEmoji = emojis[emojiIndex]
            // 중복 클릭인지 확인
            if (nowEmoji.tag == CHOOSE) {
                nowEmoji.setCount(
                    (nowEmoji.count.toInt() - 1).toString()
                )
                nowEmoji.tag = NOT_CHOOSE
                emojis.forEach {
                    it.animAlpha(1f)
                }
                pickEmoji = null
            } else {
                // 클릭된 이모지 찾기
                emojis.forEach {
                    if (it.tag == CHOOSE) {
                        it.setCount(
                            (it.count.toInt() - 1).toString()
                        )
                        it.tag = NOT_CHOOSE
                    }
                    it.animAlpha(EMOJI_ALPHA)
                }
                nowEmoji.tag = CHOOSE
                nowEmoji.setCount(
                    (nowEmoji.count.toInt() + 1).toString()
                )
                nowEmoji.animAlpha(1f)
                pickEmoji = nowEmoji
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
        if (this.alpha == EMOJI_ALPHA && alpha == EMOJI_ALPHA ) {
            return
        } else {
            this.animate()
                .alpha(alpha)
                .setDuration(200)
        }
    }

    private fun addFiles(
        files: List<FileModel>,
    ) {
        mBinding.layoutFiles.run {
            files.forEach { file ->
                val view = CustomFileDownload(requireContext(), null)
                view.apply {
                    setFileName(file.fileName)
                    setFileSize(file.fileSize)
                    setFileLink(file.fileUrl)
                    setOnClickListener {
                        downloadFile(
                            url = file.fileUrl,
                            name = file.fileName,
                            extension = file.filetype?: "*"
                        )
                    }
                }
                addView(view)
            }
        }
    }

    private fun getEmojiIndex(
        emoji: String,
    ): Int {
        var nowEmoji = emoji
        if (emoji.length < 6) {
            nowEmoji = "ON_CLICK_${emoji}"
        }

        val emojiIndex = when (nowEmoji) {
            ON_CLICK_OKAY -> 0
            ON_CLICK_ANGRY -> 1
            ON_CLICK_LAUGH -> 2
            ON_CLICK_LOVE -> 3
            ON_CLICK_SAD -> 4
            else -> 0
        }
        return emojiIndex
    }

    companion object {
        const val EMOJI_ALPHA = 0.3f
    }

}