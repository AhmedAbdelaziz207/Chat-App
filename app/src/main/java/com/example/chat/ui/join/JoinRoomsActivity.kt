package com.example.chat.ui.join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Constants
import com.example.chat.data.dataBase.model.Room
import com.example.chat.databinding.ActivityJoinRoomBinding
import com.example.chat.ui.Message
import com.example.chat.ui.chat.ChatActivity
import com.example.chat.ui.home.HomeActivity
import com.google.android.material.snackbar.Snackbar

class JoinRoomsActivity : AppCompatActivity() {
   private lateinit var viewBinding : ActivityJoinRoomBinding
    private lateinit var viewModel: JoinRoomsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityJoinRoomBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[JoinRoomsViewModel::class.java]
        setContentView(viewBinding.root)

        initViews()
    }
    private fun initViews() {
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this


       viewModel.roomLiveData.value =  getParam()
        observeOnLiveData()
    }

    private fun observeOnLiveData() {
        viewModel.joinRoomEvents.observe(this){
         handleNavigation(it)
        }

        viewModel.message.observe(this){
            showSnackBar(it)
        }
    }

    private fun showSnackBar(it: Message) {
       val snackBar = Snackbar.make(viewBinding.root, it.message.toString(),Snackbar.LENGTH_LONG)
        snackBar.show()
    }


    private fun getParam() :Room{
      return  intent.getSerializableExtra(Constants.Room) as Room
    }

    private fun handleNavigation(it: JoinNavigation) {
        when(it){
            JoinNavigation.NavigateToHome-> navigateToHome()
            JoinNavigation.NavigateToChat ->navigateToChat()
        }
    }
    private fun navigateToHome(){
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToChat(){
        val intent = Intent(this, ChatActivity::class.java)
        val room = getParam()
        intent.putExtra(Constants.Room,room)
        startActivity(intent)
        finish()
    }

}