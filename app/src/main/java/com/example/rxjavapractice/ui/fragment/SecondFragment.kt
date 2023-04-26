package com.example.rxjavapractice.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rxjavapractice.ChangeFragmentType
import com.example.rxjavapractice.add
import com.example.rxjavapractice.changeFragment
import com.example.rxjavapractice.data.MessageEvent
import com.example.rxjavapractice.databinding.FragmentSecondBinding
import com.example.rxjavapractice.printBackStackSize
import com.example.rxjavapractice.ui.base.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit

class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    override val LOG_TAG: String = this::class.java.name
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentSecondBinding =
        FragmentSecondBinding::inflate
    override var compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallbacks()
    }

    private fun addCallbacks() {
        solveButtonClickedTwiceProblem()
    }

    private fun solveButtonClickedTwiceProblem() {
        PublishSubject.create<Unit> {
            it.onNext(Unit)
        }.throttleFirst(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                ::onDataSuccess,
                ::onDataFailed
            ).add(compositeDisposable)
    }

    private fun onDataSuccess(result: Any) {
        onButtonClicked()
    }

    private fun onDataFailed(error: Throwable) {
        log(error.toString())
    }

    private fun onButtonClicked() {
        binding.buttonChangeFragmentSecond.setOnClickListener {
            this.changeFragment(ThirdFragment(), ChangeFragmentType.ADD)
            log(printBackStackSize().toString())
        }
    }
}
