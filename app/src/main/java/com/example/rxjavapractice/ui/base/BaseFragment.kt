package com.example.rxjavapractice.ui.base


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    abstract val LOG_TAG: String
    abstract val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> VB
    private var _binding: ViewBinding? = null
    protected val binding get() = _binding!! as VB
    abstract var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, requireNotNull(container), false)
        return binding.root
    }
    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }
    protected fun log(value: String) {
        Log.v(LOG_TAG, value)
    }

}