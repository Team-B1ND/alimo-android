package com.b1nd.alimo.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.b1nd.alimo.presentation.utiles.Event
import com.b1nd.alimo.presentation.utiles.repeatOnStarted
import kotlinx.coroutines.flow.collectLatest

abstract class BaseDialogFragment<T: ViewDataBinding, VM: BaseViewModel>(
    @LayoutRes val layoutRes: Int
) : Fragment() {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: VM

    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = if (::mViewModel.isInitialized) mViewModel else viewModel
        mBinding.setVariable(BR.vm, mViewModel)
        mBinding.lifecycleOwner = this@BaseDialogFragment
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        if (::mBinding.isInitialized) mBinding.unbind()
    }

    protected fun bindingViewEvent(action: (event: Event) -> Unit) {
        repeatOnStarted {
            viewModel.eventFlow.collectLatest {
                action(it)
            }
        }
    }
}