package com.example.rxjavapractice.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import com.example.rxjavapractice.add
import com.example.rxjavapractice.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compositeDisposable = CompositeDisposable()

        foo()
    }

    private fun foo() {
        val singleObservable = Single.just(6)
        singleObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
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

    private fun Declarative() {
        val happy = Observable.just(5, 7, 10, 123)
        happy.subscribe()
    }

    private fun Imperative() {
        var sad = 5
        Log.v(LOG_TAG, sad.toString())
        sad = 7
        Log.v(LOG_TAG, sad.toString())
        sad = 20
        Log.v(LOG_TAG, sad.toString())
        sad = 80
        Log.v(LOG_TAG, sad.toString())
        sad = 123
        Log.v(LOG_TAG, sad.toString())
    }


    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    companion object {
        val LOG_TAG: String = this::class.java.name
    }


}

