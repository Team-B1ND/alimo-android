package com.b1nd.alimo.feature.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.b1nd.alimo.custom.CustomCategoryCard
import com.b1nd.alimo.databinding.FragmentProfileBinding

class TestFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding

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
            Log.d("TAG", "onCreateView: $it")
        }
        return binding.root
    }
}