package com.b1nd.alimo.presentation.feature.main.post

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.CustomPopupEmojiBinding
import com.b1nd.alimo.presentation.utiles.Dlog

class PostEmojiPopup(
    context: Context,
    popupList: List<PostEmojiPopUpModel>,
    menuItemListener: (PostEmojiPopUpModel) -> Unit

): PopupWindow(context) {

    init {
        val inflater = LayoutInflater.from(context)

        val binding = CustomPopupEmojiBinding.inflate(inflater, null, false)

        // PopupWindow Value Setting
//        width = binding.layoutPopup.width
        contentView = binding.root
        width = getDp(context, 270.0f)


        for (i in 0 until popupList.size) {
            val view = inflater.inflate(R.layout.item_popup_emoji, null, false)

            val ivMenuIcon = view.findViewById(R.id.image_popup_emoji) as ImageView
            ivMenuIcon.setImageResource(popupList[i].resId)

            view.setOnClickListener {
                menuItemListener(popupList[i])
                dismiss()
            }

            binding.layoutPopup.addView(view)
        }
        isFocusable = true
        isOutsideTouchable = true
        contentView.setOnFocusChangeListener { view, b ->
            dismiss()
            Dlog.d("focus Change")
        }

//        val layout = contentView
//        layout.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        height = layout.measuredHeight

        setBackgroundDrawable(ContextCompat.getDrawable(context, com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
    }

    private fun getDp(context: Context, value: Float): Int {
        val dm = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm).toInt()
    }
}

data class PostEmojiPopUpModel(
    val title: String,
    val resId: Int
)