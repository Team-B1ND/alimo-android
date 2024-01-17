package com.b1nd.alimo.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import com.b1nd.alimo.utiles.Event
import com.b1nd.alimo.utiles.repeatOnStarted
import kotlinx.coroutines.flow.collectLatest

abstract class BaseActivity<T: ViewDataBinding, VM: BaseViewModel>(
    @LayoutRes val layoutRes: Int
) : AppCompatActivity() {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: VM

    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutRes)
        performDataBinding()
    }

    private fun performDataBinding() {
        mViewModel = if (::mViewModel.isInitialized) mViewModel else viewModel
        mBinding.setVariable(BR.vm, mViewModel)
        mBinding.lifecycleOwner = this@BaseActivity
        initView()
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