package com.b1nd.alimo.presentation.custom

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.CustomSnakbarBinding
import com.google.android.material.snackbar.Snackbar

class CustomSnackBar(view: View, private val message: String) {

    companion object {

        fun make(view: View, message: String) = CustomSnackBar(view, message)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", 5000)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)

    private val snackbarBinding: CustomSnakbarBinding = DataBindingUtil.inflate(inflater, R.layout.custom_snakbar, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
//            removeAllViews()
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarBinding.textMessage.text = message
        snackbarBinding.textClose.setOnClickListener {
            snackbar.dismiss()
        }
    }

    fun show() {
        snackbar.show()
    }
}