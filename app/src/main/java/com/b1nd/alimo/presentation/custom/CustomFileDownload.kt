package com.b1nd.alimo.presentation.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.CustomFileDownloadBinding

class CustomFileDownload(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {
    private var fileLink: String? = null
    private lateinit var onClickView: (String) -> Unit

    constructor(
        context: Context,
        onClick: (String) -> Unit
    ) : this(context, null) {
        onClickView = onClick
    }


    private val binding: CustomFileDownloadBinding by lazy {
        CustomFileDownloadBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.custom_file_download, this, false)
        )
    }

    init {
        initView()
        initAttrs(attrs)
    }

    private fun initView() {
        addView(binding.root)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_file_download)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(
        typedArray: TypedArray
    ) {
        binding.run {
            textFileName.text = typedArray.getString(R.styleable.custom_file_download_fileName)?: ""
            textFileSize.text = typedArray.getString(R.styleable.custom_file_download_fileSize)?: ""
            fileLink = typedArray.getString(R.styleable.custom_file_download_link)
            layoutFileDownload.setOnClickListener {
                if (fileLink != null) {
                    onClickView?.invoke(fileLink!!)
                }
            }
        }
        typedArray.recycle()
    }

    fun setFileName(
        name: String
    ) {
        binding.textFileName.text = name
    }

    fun setFileSize(
        size: String
    ) {
        binding.textFileSize.text = size
    }

    fun setFileLink(
        link: String
    ) {
        fileLink = link
    }

    fun setOnClickListener(
        onClick: (String) -> Unit
    ) {
        onClickView = onClick
    }
}