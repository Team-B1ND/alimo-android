package com.b1nd.alimo.feature.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.ItemPostBinding
import com.b1nd.alimo.utiles.loadImage
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
                    imageProfile.loadImage(item.authorProfile)
                }
                if (item.image != null) {
                    imageContent.visibility = View.VISIBLE
                    imageContent.loadImage(item.image)
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