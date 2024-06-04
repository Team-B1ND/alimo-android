package com.b1nd.alimo.presentation.feature.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.b1nd.alimo.databinding.FragmentProfileBinding
import com.b1nd.alimo.presentation.custom.CustomCategoryCard
import com.b1nd.alimo.presentation.custom.CustomSnackBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var dialog: TestDialog? = null

    override fun onStart() {
        super.onStart()
        dialog = TestDialog()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        for (i in 1..20) {
            binding.layoutCategory.addView(CustomCategoryCard(requireContext(), null, "qwe"))
        }
        binding.layoutCategory.addView(CustomCategoryCard(requireContext(), null, "qwewqe"))
        binding.cardAlarm.setSwitchOnClickListener {
        }
        lifecycleScope.launch(Dispatchers.Main) {
            delay(1000)
//            delay(5000)
            dialog?.show(super.getChildFragmentManager(), "")
            delay(1000)
            //requireActivity().window.decorView
            CustomSnackBar.make(requireView(), "복사에 성공하였습니다!").show()
        }
        return binding.root
    }
}

