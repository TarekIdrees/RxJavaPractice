package com.example.rxjavapractice

import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


fun Disposable.add(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun Fragment.changeFragment(fragment: Fragment, changeType: ChangeFragmentType) {
    when (changeType) {
        ChangeFragmentType.ADD -> {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, fragment)
                addToBackStack(fragment::class.java.name)
                commit()
            }
        }
        ChangeFragmentType.REPLACE -> {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, fragment)
                commit()
            }
        }
        ChangeFragmentType.REMOVE -> {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                remove(fragment)
                commit()
            }
        }
    }
}