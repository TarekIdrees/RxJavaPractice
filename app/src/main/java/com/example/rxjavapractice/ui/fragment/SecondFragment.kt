package com.example.rxjavapractice.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rxjavapractice.ChangeFragmentType
import com.example.rxjavapractice.changeFragment
import com.example.rxjavapractice.databinding.FragmentSecondBinding
import com.example.rxjavapractice.ui.base.BaseFragment

class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    override val LOG_TAG: String = this::class.java.name
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentSecondBinding =
        FragmentSecondBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addCallbacks()
    }

    private fun addCallbacks() {
        onButtonClicked()
    }

    private fun onButtonClicked() {
        binding.buttonChangeFragmentSecond.setOnClickListener {
            this.changeFragment(ThirdFragment(), ChangeFragmentType.ADD)
        }
    }
}