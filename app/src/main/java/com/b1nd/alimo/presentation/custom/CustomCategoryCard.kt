package com.b1nd.alimo.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.b1nd.alimo.R

class CustomCategoryCard(context: Context, attrs: AttributeSet?, mName: String? = null) : ConstraintLayout(context, attrs) {
    private lateinit var name: String

    init {
        initAttrs(attrs)
        if (mName != null) {
            name = mName
        }
        initView()
    }

    private fun initAttrs(attrs: AttributeSet?) {
//        attrs?.let {  }
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.custom_category_card,
            0, 0
        ).apply {
            // 속성으로 전달받은 값을 대입하는 부분
            try {
                name = getString(R.styleable.custom_category_card_name) ?: ""
            } finally {
                recycle()
            }
        }
    }

    private fun initView() {
        inflate(context, R.layout.custom_category_card, this)

        val nameTextView = findViewById<TextView>(R.id.text_name)

        nameTextView.text = name
    }
}