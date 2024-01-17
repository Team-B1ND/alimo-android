package com.b1nd.alimo.feature.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.databinding.ItemCommentBinding
import com.b1nd.alimo.utiles.loadImage
import java.time.LocalDateTime

class DetailCommentRv: PagingDataAdapter<DetailCommentItem, DetailCommentRv.ViewHolder>(diffCallback) {

    inner class ViewHolder(binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        getItem(position)?.let {
            binding.textUserName.text = it.author
            binding.imageUserProfile.loadImage(it.authorProfile)
            binding.textUserComment.text = it.content
            binding.textUserDatetime.text = it.createAt.toString()
            it.comments?.let {
                binding.rvCommentComment.visibility = View.VISIBLE
                binding.rvCommentComment.adapter = DetailCommentCommentRv(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<DetailCommentItem>() {
            override fun areItemsTheSame(oldItem: DetailCommentItem, newItem: DetailCommentItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DetailCommentItem, newItem: DetailCommentItem): Boolean =
                oldItem == newItem
        }
    }
}



data class DetailCommentItem(
    val id: Int,
    val author: String,
    val authorProfile: String,
    val createAt: LocalDateTime,
    val content: String,
    val comments: List<DetailCommentItem>?
)