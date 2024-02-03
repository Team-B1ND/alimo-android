package com.b1nd.alimo.presentation.feature.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.databinding.ItemCommentCommentBinding
import com.b1nd.alimo.presentation.utiles.loadImage

class DetailCommentCommentRv constructor(
    private val items: List<DetailCommentItem>
): RecyclerView.Adapter<DetailCommentCommentRv.ViewHolder>() {

    inner class ViewHolder(binding: ItemCommentCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCommentCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        items[position].let {
            binding.textUserName.text = it.author
            binding.imageUserProfile.loadImage(it.authorProfile)
            binding.textUserComment.text = it.content
            binding.textUserDatetime.text = it.createAt.toString()
        }
    }
}