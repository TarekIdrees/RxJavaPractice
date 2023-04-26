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
import com.example.rxjavapractice.data.MessageEvent
import com.example.rxjavapractice.databinding.FragmentHomeBinding
import com.example.rxjavapractice.printBackStackSize
import com.example.rxjavapractice.ui.base.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val LOG_TAG: String = this::class.java.name
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHomeBinding
       get() =  FragmentHomeBinding::inflate
    override var compositeDisposable = CompositeDisposable()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCallbacks()

    }

    private fun addCallbacks() {
        onButtonClicked()
        solveSearchProblem()
        onButtonSendMessageClicked()
    }

    private fun onButtonSendMessageClicked() {
        binding.buttonSendMessage.setOnClickListener {
            val message = binding.editTextMessage.text.toString()
            val newMessageToSend = MessageEvent(message)
            EventBus.getDefault().postSticky(newMessageToSend)
        }
    }

    private fun onButtonClicked() {
        binding.buttonChangeFragmentHome.setOnClickListener {
            this.changeFragment(SecondFragment(), ChangeFragmentType.ADD)
            log(printBackStackSize().toString())
        }
    }

    private fun solveSearchProblem() {
        val observable = Observable.create { emitter ->
            binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
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

}