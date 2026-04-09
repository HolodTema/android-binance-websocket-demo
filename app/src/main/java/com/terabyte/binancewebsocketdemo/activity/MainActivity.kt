package com.terabyte.binancewebsocketdemo.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.terabyte.binancewebsocketdemo.R
import com.terabyte.binancewebsocketdemo.adapter.MessageAdapter
import com.terabyte.binancewebsocketdemo.databinding.ActivityMainBinding
import com.terabyte.binancewebsocketdemo.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureWindowInsets()

        val messageAdapter = MessageAdapter(layoutInflater, emptyList())
        binding.recyclerMessages.adapter = messageAdapter

        binding.buttonConnect.setOnClickListener {
            viewModel.connectWebsocket()
        }

        binding.buttonDisconnect.setOnClickListener {
            viewModel.disconnectWebsocket()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlowConnectionStatus.collect { connectionStatus ->
                    binding.textConnectionStatus.text = "Connection status: ${connectionStatus.name}"
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sharedFlowMessages.collect { messageModel ->
                    messageAdapter.messages = messageAdapter.messages.plus(messageModel)
                    messageAdapter.notifyItemInserted(messageAdapter.messages.lastIndex)
                }
            }
        }
    }

    private fun configureWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}