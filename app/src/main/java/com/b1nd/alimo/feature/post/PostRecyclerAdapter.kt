package com.b1nd.alimo.feature.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.ItemPostBinding
import com.bumptech.glide.Glide
import java.time.LocalDateTime

class PostRecyclerAdapter constructor(
    private val onClick: (PostItem) -> Unit
): PagingDataAdapter<PostItem, PostRecyclerAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }


    override fun onBindViewHolder(holder: PostRecyclerAdapter.ViewHolder, position: Int) {
        val binding = holder.binding
        val item = getItem(position)
        item?.let {
            with(binding) {
                if (item.authorProfile != null) {
                    Glide.with(root)
                        .load(item.authorProfile)
                        .centerCrop()
                        .into(imageProfile)
                }
                if (item.image != null) {
                    imageContent.visibility = View.VISIBLE
                    Glide.with(root)
                        .load(item.image)
                        .centerCrop()
                        .into(imageContent)
                }
                if (item.isNew) {
                    imageNewBadge.visibility = View.VISIBLE
                }
                if (item.isBookmark) {
                    imageBookmark.setImageResource(R.drawable.ic_bookmark)
                }

                textAuthor.text = item.author
                textDate.text = item.createAt.toString()
                textContent.text = item.content
            }
            binding.layoutPost.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostRecyclerAdapter.ViewHolder {
        return ViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PostItem>() {
            override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean =
                oldItem == newItem
        }
    }
}

data class PostItem(
    val id: Int,
    val author: String,
    val authorProfile: String?,
    val createAt: LocalDateTime,
    val content: String,
    val image: String?,
    val isBookmark: Boolean,
    val isNew: Boolean
)