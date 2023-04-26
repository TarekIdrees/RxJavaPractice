package com.example.rxjavapractice.ui.activity

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rxjavapractice.AirplaneModeChangedReceiver
import com.example.rxjavapractice.databinding.ActivityMainBinding
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var receiver: AirplaneModeChangedReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        addCallbacks()
    }

    private fun init() {
        receiver = AirplaneModeChangedReceiver()
    }

    private fun addCallbacks() {
        setupAirplaneReceiver()
    }

    private fun setupAirplaneReceiver() {
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

}

