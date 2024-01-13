package com.b1nd.alimo.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import com.b1nd.alimo.R

class CustomSettingCard(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private lateinit var title: String
    private lateinit var description: String
    private var titleColor: Int = Color.BLACK
    private var switchVisible: Boolean = false
    private var switchEnable: Boolean = true
    private var switchChecked: Boolean = false

    init {
        initAttrs(attrs)
        initView()
    }

    private fun initAttrs(attrs: AttributeSet?) {
//        attrs?.let {  }
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.custom_setting_card,
            0, 0
        ).apply {
            // 속성으로 전달받은 값을 대입하는 부분
            try {
                title = getString(R.styleable.custom_setting_card_title) ?: ""
                titleColor = getColor(R.styleable.custom_setting_card_titleColor, Color.BLACK)
                description = getString(R.styleable.custom_setting_card_description) ?: ""
                switchVisible = getBoolean(R.styleable.custom_setting_card_switchVisible, false)
                switchEnable = getBoolean(R.styleable.custom_setting_card_switchEnable, true)
                switchChecked = getBoolean(R.styleable.custom_setting_card_switchChecked, false)

            } finally {
                recycle()
            }
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun initView() {
        inflate(context, R.layout.custom_setting_card, this)

        val titleTextView = findViewById<TextView>(R.id.text_setting_title)
        val descriptionTextView = findViewById<TextView>(R.id.text_setting_description)
        val switch = findViewById<Switch>(R.id.switch_setting)

        titleTextView.text = title
        titleTextView.setTextColor(titleColor)

        if (description.isNotEmpty()) {
            descriptionTextView.text = description
            descriptionTextView.visibility = View.VISIBLE
        }

        if (switchVisible) {
            switch.visibility = View.VISIBLE
        }
        switch.isEnabled = switchEnable
        switch.isChecked = switchChecked
    }

    fun setDescriptionText(text: String) {
        val descriptionTextView = findViewById<TextView>(R.id.text_setting_description)
        descriptionTextView.text = text
        descriptionTextView.visibility = View.VISIBLE
    }

    fun setTitleText(text: String) {
        val titleTextView = findViewById<TextView>(R.id.text_setting_title)
        titleTextView.text = text
    }

    fun setTitleColor(@ColorInt color: Int) {
        val titleTextView = findViewById<TextView>(R.id.text_setting_title)
        titleTextView.setTextColor(color)
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    fun setSwitchEnable(enable: Boolean) {
        val switch = findViewById<Switch>(R.id.switch_setting)
        switch.isEnabled = enable
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    fun setSwitchChecked(checked: Boolean) {
        val switch = findViewById<Switch>(R.id.switch_setting)
        switch.isChecked = checked
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    fun setSwitchVisible(visible: Boolean) {
        val switch = findViewById<Switch>(R.id.switch_setting)
        switch.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    fun setSwitchOnClickListener(onClick: (checked: Boolean) -> Unit) {
        val switch = findViewById<Switch>(R.id.switch_setting)
        switch.setOnClickListener {
            onClick(switch.isChecked)
        }
    }




}