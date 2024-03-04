package com.b1nd.alimo.presentation.feature.main.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.data.model.SubCommentModel
import com.b1nd.alimo.databinding.ItemCommentCommentBinding
import com.b1nd.alimo.presentation.utiles.loadImage

class DetailCommentCommentRv constructor(
    private val items: List<SubCommentModel>
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
            binding.textUserName.text = it.commenter
            if (it.profileImage != null) {
                binding.imageUserProfile.loadImage(it.profileImage)
            }
            binding.textUserComment.text = it.content
            binding.textUserDatetime.text = it.createdAt.toString()
            Log.d("TAG", "onBindViewHolder: ${items.size - position}")
            if (items.size - position != 1) { // 마지막 댓글인 경우 선 안보이도록
                binding.imageLine.visibility = View.VISIBLE
            }
        }
    }
}