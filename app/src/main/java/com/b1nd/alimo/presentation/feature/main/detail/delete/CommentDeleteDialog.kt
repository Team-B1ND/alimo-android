package com.b1nd.alimo.presentation.feature.main.detail.delete

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.DialogCommentDeleteBinding
import com.b1nd.alimo.presentation.base.BaseDialogFragment
import com.b1nd.alimo.presentation.feature.main.detail.delete.CommentDeleteViewModel.Companion.ON_CLICK_CLOSE
import com.b1nd.alimo.presentation.feature.main.detail.delete.CommentDeleteViewModel.Companion.ON_CLICK_DELETE
import com.b1nd.alimo.presentation.utiles.onSuccessEvent

class CommentDeleteDialog(
    private val onClickDelete: () -> Unit
): BaseDialogFragment<DialogCommentDeleteBinding, CommentDeleteViewModel>(R.layout.dialog_comment_delete) {

    override val viewModel: CommentDeleteViewModel by viewModels()

    override fun initView() {
        bindingViewEvent {  event ->
            event.onSuccessEvent {
                when(it) {
                    ON_CLICK_DELETE -> {
                        onClickDelete()
                        dismiss()
                    }
                    ON_CLICK_CLOSE -> {
                        dismiss()
                    }
                }
            }
        }
    }
}