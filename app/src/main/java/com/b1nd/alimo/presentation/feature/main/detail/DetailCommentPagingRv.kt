package com.b1nd.alimo.presentation.feature.main.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.data.model.CommentModel
import com.b1nd.alimo.databinding.ItemCommentBinding
import com.b1nd.alimo.presentation.utiles.loadImage

class DetailCommentPagingRv constructor(
    private val onClickReply: (CommentModel) -> Unit
): PagingDataAdapter<CommentModel, DetailCommentPagingRv.ViewHolder>(diffCallback) {

    inner class ViewHolder(binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        getItem(position)?.let {
            binding.textUserName.text = it.commenter
            if (it.profileImage != null) {
                binding.imageUserProfile.loadImage(it.profileImage)
            }
            binding.textUserComment.text = it.content
            binding.textUserDatetime.text = it.createdAt.toString()

            if (it.subComments.isNotEmpty()) {
                binding.imageLine.visibility = View.VISIBLE
                binding.rvCommentComment.visibility = View.VISIBLE
                binding.rvCommentComment.adapter = DetailCommentCommentRv(it.subComments)
            }

            binding.textUserReplyButton.setOnClickListener {  view ->
                onClickReply(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CommentModel>() {
            override fun areItemsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean =
                oldItem.commentId == newItem.commentId

            override fun areContentsTheSame(oldItem: CommentModel, newItem: CommentModel): Boolean =
                oldItem == newItem
        }
    }
}



//data class DetailCommentItem(
//    val id: Int,
//    val author: String,
//    val authorProfile: String,
//    val createAt: LocalDateTime,
//    val content: String,
//    val comments: List<DetailCommentItem>?
//)