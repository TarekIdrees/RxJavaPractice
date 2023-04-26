package com.example.rxjavapractice.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rxjavapractice.ChangeFragmentType
import com.example.rxjavapractice.add
import com.example.rxjavapractice.changeFragment
import com.example.rxjavapractice.databinding.FragmentThirdBinding
import com.example.rxjavapractice.ui.base.BaseFragment
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {
    override val LOG_TAG: String = this::class.java.name
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentThirdBinding =
        FragmentThirdBinding::inflate
    override var compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallbacks()
    }

    private fun addCallbacks() {
        solveProblemOfCharacterFromAtoZwithOneSecond()
        onNextFragmentButtonPressed()
    }

    private fun onNextFragmentButtonPressed() {
        binding.buttonChangeFragmentThird.setOnClickListener {
            this.changeFragment(FourthFragment(), ChangeFragmentType.ADD)
        }
    }

    private fun solveProblemOfCharacterFromAtoZwithOneSecond() {
        Observable.fromIterable('a'..'z')
            .zipWith(Observable.interval(1, TimeUnit.SECONDS)) { char, _ -> char }
            .subscribe(
                ::onDataSuccess,
                ::onDataFailed
            ).add(compositeDisposable)
    }

    private fun onDataSuccess(result: Any) {
        log("result -> $result")
    }

    private fun onDataFailed(error: Throwable) {
        log("error = $error")
    }
}