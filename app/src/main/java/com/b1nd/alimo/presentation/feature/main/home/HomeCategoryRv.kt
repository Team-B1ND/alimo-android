package com.b1nd.alimo.presentation.feature.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.ItemCategoryBinding

class HomeCategoryRv constructor(
    private val items: List<HomeCategoryRvItem>,
    private val context: Context,
    private val onClick: (HomeCategoryRvItem, Int) -> Unit
): RecyclerView.Adapter<HomeCategoryRv.ViewHolder>() {
    private var nowChooseItem: ViewHolder? = null
    inner class ViewHolder(binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.run {
            if (position != 0) {
                layoutCategory.setBackgroundResource(R.drawable.ripple_gray100_12)
                text.setTextColor(context.getColor(R.color.Gray500))
            } else {
                imageBadge.visibility = View.GONE
                nowChooseItem = holder
            }
            text.text = items[position].category
            imageBadge.visibility = if (items[position].new) View.VISIBLE else View.GONE
            layoutCategory.setOnClickListener {
                if (holder == nowChooseItem) {
                    return@setOnClickListener
                }
                layoutCategory.setBackgroundResource(R.drawable.ripple_main500_12)
                text.setTextColor(context.getColor(R.color.Main900))
                imageBadge.visibility = View.GONE
                nowChooseItem?.binding?.layoutCategory?.setBackgroundResource(R.drawable.ripple_gray100_12)
                nowChooseItem?.binding?.text?.setTextColor(context.getColor(R.color.Gray500))
                nowChooseItem = holder
                onClick(items[position], position)
            }
        }
    }

    fun setNowChooseItem(viewHolder: ViewHolder) {
        nowChooseItem = viewHolder
    }
}

data class HomeCategoryRvItem(
    val category: String,
    val new: Boolean
)