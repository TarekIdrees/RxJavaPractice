package com.example.rxjavapractice.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.rxjavapractice.databinding.FragmentThirdBinding
import com.example.rxjavapractice.ui.base.BaseFragment

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {
    override val LOG_TAG: String = this::class.java.name
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentThirdBinding =
        FragmentThirdBinding::inflate
}