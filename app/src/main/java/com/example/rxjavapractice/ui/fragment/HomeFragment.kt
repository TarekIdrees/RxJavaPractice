package com.example.rxjavapractice.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.rxjavapractice.ChangeFragmentType
import com.example.rxjavapractice.add
import com.example.rxjavapractice.changeFragment
import com.example.rxjavapractice.databinding.FragmentHomeBinding
import com.example.rxjavapractice.ui.base.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val LOG_TAG: String = this::class.java.name
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate
    lateinit var compositeDisposable: CompositeDisposable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        compositeDisposable = CompositeDisposable()
        solveSearchProblem()
        addCallbacks()
    }

    private fun addCallbacks() {
        onButtonClicked()
    }

    private fun onButtonClicked() {
        binding.buttonChangeFragmentHome.setOnClickListener {
            this.changeFragment(SecondFragment(), ChangeFragmentType.ADD)
        }
    }

    private fun solveSearchProblem() {
        val observable = Observable.create { emitter ->
            binding.editTextSearch.doOnTextChanged { text, start, before, count ->
                emitter.onNext(text.toString())
            }
        }.debounce(2, TimeUnit.SECONDS)

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
            ::onDataSuccess,
            ::onDataFailed
        ).add(compositeDisposable)

    }

    private fun onDataSuccess(result: Any) {
        Log.v(LOG_TAG, "result -> $result")
    }

    private fun onDataFailed(error: Throwable) {
        Log.v(LOG_TAG, "error = $error")
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}