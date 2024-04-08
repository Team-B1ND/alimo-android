package com.b1nd.alimo.presentation.feature.main.post

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.R
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.databinding.ItemPostBinding
import com.b1nd.alimo.presentation.utiles.loadImage
import com.b1nd.alimo.presentation.utiles.setImageResourceAndClearTint
import com.b1nd.alimo.presentation.utiles.toDateString
import com.bumptech.glide.Glide

class PostRecyclerAdapter constructor(
    private val context: Context,
    private val onClickBookmark: (notificationId: Int) -> Unit,
    private val onClickEmoji: (notificationId: Int, emoji: String) -> Unit,
    private val onClick: (NotificationModel) -> Unit
): PagingDataAdapter<NotificationModel, PostRecyclerAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }


    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.itemView.context).clear(holder.binding.imageContent)
        Glide.with(holder.itemView.context).clear(holder.binding.imageProfile)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val item = getItem(position)
        item?.let {
            with(binding) {
                if (item.memberProfile != null) {
                    imageProfile.loadImage(item.memberProfile)
                }
                if (item.images.isNotEmpty()) {
                    imageContent.visibility = View.VISIBLE
                    imageContent.loadImage(item.images[0].fileUrl)
                }
                if (item.files.isNotEmpty()) {
                    val file = item.files[0]
                    layoutFile.isVisible = true
                    textFileName.text = file.fileName
                    textFileCount.text = "총 ${item.files.size}개 파일"
                }
                if (item.isNew) {
                    imageNewBadge.visibility = View.VISIBLE
                }
                if (item.isBookmark) {
                    imageBookmark.setBookmark(true)
                }

                if (item.emoji != null) {
                    val resource = when(item.emoji) {
                        "ANGRY" -> R.drawable.ic_angry
                        "LAUGH" -> R.drawable.ic_laugh
                        "LOVE" -> R.drawable.ic_love
                        "OKAY" -> R.drawable.ic_okay
                        else -> R.drawable.ic_sad
                    }
                    imageAddEmoji.setImageResourceAndClearTint(resource)
                    imageAddEmoji.tag = item.emoji
                }
                val menu = PostEmojiPopup(context, emojis) {
                    Log.d("TAG", "onBindViewHolder: $it")
                    onClickEmoji(item.notificationId, it.title)
                    Log.d("TAG", "onBindViewHolder: $")
                    if (imageAddEmoji.tag == it.title) {
                        imageAddEmoji.tag = "not_emoji"
                        imageAddEmoji.setImageResource(R.drawable.ic_add_emoji)
                        imageAddEmoji.imageTintList = ColorStateList.valueOf(context.getColor(R.color.Gray500))
                    } else {
                        imageAddEmoji.tag = it.title
                        imageAddEmoji.setImageResourceAndClearTint(it.resId)
                    }
                }
                textTitle.text = item.title
                textAuthor.text = item.member
                textDate.text = item.createdAt.toDateString()
                textContent.text = item.content

                imageAddEmoji.setOnClickListener {
                    menu.showAsDropDown(imageAddEmoji)
//                    onClickEmoji(item.notificationId, item.emoji)
                }

                imageBookmark.setOnClickListener {
                    imageBookmark.setBookmark(imageBookmark.tag != "bookmark")
                    onClickBookmark(item.notificationId)
                }

                layoutPost.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }

    private fun ImageView.setBookmark(isBookmarked: Boolean) {
        this.tag = if (isBookmarked) "bookmark" else "not_bookmark"
        this.setImageResource(if (isBookmarked) R.drawable.ic_bookmark else R.drawable.ic_not_bookmark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NotificationModel>() {
            override fun areItemsTheSame(oldItem: NotificationModel, newItem: NotificationModel): Boolean =
                oldItem.notificationId == newItem.notificationId

            override fun areContentsTheSame(oldItem: NotificationModel, newItem: NotificationModel): Boolean =
                oldItem == newItem
        }
        private val emojis = listOf(
            PostEmojiPopUpModel("ANGRY", R.drawable.ic_angry),
            PostEmojiPopUpModel("LAUGH", R.drawable.ic_laugh),
            PostEmojiPopUpModel("LOVE", R.drawable.ic_love),
            PostEmojiPopUpModel("OKAY", R.drawable.ic_okay),
            PostEmojiPopUpModel("SAD", R.drawable.ic_sad),
        )
    }
}
