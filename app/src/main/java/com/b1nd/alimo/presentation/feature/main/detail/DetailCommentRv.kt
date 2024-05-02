package com.b1nd.alimo.presentation.feature.main.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.data.model.CommentModel
import com.b1nd.alimo.databinding.ItemCommentBinding
import com.b1nd.alimo.presentation.utiles.loadImage
import com.b1nd.alimo.presentation.utiles.toDateString

class DetailCommentRv constructor(
    private val items: List<CommentModel>,
    private val onClickReply: (CommentModel) -> Unit
): RecyclerView.Adapter<DetailCommentRv.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        items[position].let {
            binding.textUserName.text = it.commenter
            if (it.profileImage != null) {
                binding.imageUserProfile.loadImage(it.profileImage)
            }
            binding.textUserComment.text = it.content
            binding.textUserDatetime.text = it.createdAt.toDateString()

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

    override fun getItemCount(): Int = items.size

}

