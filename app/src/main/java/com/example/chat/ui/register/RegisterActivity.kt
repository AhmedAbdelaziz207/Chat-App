package com.example.chat.ui.register

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chat.databinding.ActivityRegisterBinding
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.login.LoginActivity
import com.example.chat.ui.showDialog
import com.google.firebase.FirebaseApp

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        initViews()

    }

    private fun initViews() {
        FirebaseApp.initializeApp(this)

        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this

        setBackButton()
        observeOnLiveData()


    }

    private fun observeOnLiveData() {
        viewModel.messageLiveData.observe(this) {
            showDialog(
                message = it.message.toString()
              , posAction = it.posAction, posMessage = it.posMessage,
                negAction = it.negAction, negMessage = it.negMessage
                ,isCancellable = true)
        }

        viewModel.navigateLiveData.observe(this){
                checkNavigation(it)
            }
        }



    private fun checkNavigation(it: RegisterNavigation?) {
        when(it){
            RegisterNavigation.NavigateToLogin->{
                openNewActivity(LoginActivity())
            }

            RegisterNavigation.NavigateToHome->{
                openNewActivity(HomeActivity())
            }

            else -> {}
        }
}

    private fun setBackButton() {
        viewBinding.backIc.setOnClickListener{
            openNewActivity(LoginActivity())}
        }

    private fun openNewActivity(activity: Activity) {

            val intent = Intent(this , activity::class.java)
            startActivity(intent)
            finish()

}
}