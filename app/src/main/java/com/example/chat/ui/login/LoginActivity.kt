package com.example.chat.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chat.databinding.ActivityLoginBinding
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.register.RegisterActivity
import com.example.chat.ui.showDialog

class LoginActivity : AppCompatActivity() {
 private  lateinit var viewBinding :ActivityLoginBinding
 private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        initViews()
    }

    private fun initViews() {
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this

        observeOnLiveData()

    }

    private fun observeOnLiveData() {
        viewModel.messageLiveData.observe(this){
            showDialog(message = it.message.toString()
            , posAction = it.posAction, posMessage = it.posMessage,
                negMessage = it.negMessage, negAction = it.negAction)
        }

        viewModel.navigateLiveData.observe(this){
            when(it){
                LoginNavigation.NavigateToHome->{
                    openNewActivity(HomeActivity())
                }
                LoginNavigation.NavigateToRegister->{
                    openNewActivity(RegisterActivity())
                }
            }
        }

    }


    private fun openNewActivity(activity:Activity) {
        val intent = Intent(this , activity::class.java)
        startActivity(intent)
        finish()
    }
}