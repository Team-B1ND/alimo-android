package com.b1nd.alimo.presentation

import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.presentation.base.BaseActivity
import com.b1nd.alimo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        val navController = navHostFragment.navController

        mBinding.navBottom
            .setupWithNavController(navController)
    }

    fun bottomVisible(state: Boolean) {
        mBinding.navBottom.visibility = if (state) View.VISIBLE else View.GONE
    }
}