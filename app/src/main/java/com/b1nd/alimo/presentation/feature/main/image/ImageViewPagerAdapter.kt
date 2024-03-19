package com.b1nd.alimo.presentation.feature.main.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.b1nd.alimo.data.model.FileModel
import com.b1nd.alimo.databinding.ItemImageBinding
import com.b1nd.alimo.presentation.utiles.loadNotCropImage

class ImageViewPagerAdapter(
    private val imageList: List<FileModel>,
    private val onClickImage: () -> Unit
): RecyclerView.Adapter<ImageViewPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val item = imageList[position]
        holder.binding.run {
            imageContent.loadNotCropImage(item.fileUrl)
            imageContent.setOnViewTapListener { view, x, y ->
                onClickImage()
            }
        }
    }

    inner class PagerViewHolder(binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root){
        val binding = binding
    }
}