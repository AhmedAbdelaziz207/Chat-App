package com.example.chat.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.Constants
import com.example.chat.data.dataBase.model.Room
import com.example.chat.databinding.ActivityChatBinding
import com.example.chat.ui.home.HomeActivity

class ChatActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityChatBinding
    lateinit var viewModel: ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        initViews()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllMessages()
        viewModel.listenForNewMessages()
    }

    private lateinit var adapter: ChatAdapter

    private fun initViews() {
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this

        viewModel.room = getParam()
        adapter = ChatAdapter()
        viewBinding.content.recyclerView.adapter = adapter
        viewBinding.content.recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        observeOnLiveData()
    }

    private fun observeOnLiveData() {
        viewModel.toast.observe(this){
            if (it){
                Toast.makeText(this,"Something went wrong..",Toast.LENGTH_LONG).show()
            }
        }

        viewModel.chatEvent.observe(this){
            if (it){
                navigateToHome()
            }
        }

        viewModel.newMessages.observe(this){newMessage->
            adapter.addNewMessages(newMessage)
            viewBinding.content.recyclerView.smoothScrollToPosition(
                adapter.itemCount
            )
        }

        viewModel.messagesLiveData.observe(this){messages->
            adapter.bindData(messages)

        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getParam(): Room {
        return intent.getSerializableExtra(Constants.Room) as Room
    }
}