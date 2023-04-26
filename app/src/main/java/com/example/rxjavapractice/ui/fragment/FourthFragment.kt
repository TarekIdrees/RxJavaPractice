package com.example.rxjavapractice.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rxjavapractice.data.MessageEvent
import com.example.rxjavapractice.databinding.FragmentFourthBinding
import com.example.rxjavapractice.ui.base.BaseFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FourthFragment : BaseFragment<FragmentFourthBinding>() {
    override val LOG_TAG: String
        get() = this::class.java.name
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentFourthBinding
        get() = FragmentFourthBinding::inflate
    override var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var messageToDisplay = "No message yet"

    private fun assignNewMessage() {
        if (messageToDisplay.isNotEmpty()) {
            binding.textViewFourthFragment.text = messageToDisplay
            log("message from home = $messageToDisplay")
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onReceiveMessage(message: MessageEvent) {
        messageToDisplay = message.message.toString()
        assignNewMessage()
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }
}