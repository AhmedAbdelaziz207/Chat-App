package com.example.chat.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.example.chat.databinding.ActivitySplashBinding
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.redirect()

        },2000)

        observeOnLivData()

    }

    private fun observeOnLivData() {
        viewModel.splashEvent.observe(this){
            when(it){
                SplashNavigation.NavigateToLogin->navigateToLogin()

                SplashNavigation.NavigateToHome->navigateToHome()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this , LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToHome() {
        val intent = Intent(this , HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}