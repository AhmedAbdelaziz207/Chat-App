package com.example.chat.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.chat.R
import com.example.chat.databinding.ActivityHomeBinding
import com.example.chat.ui.addRoom.AddRoomActivity
import com.example.chat.ui.browse.BrowseRoomsFragment
import com.example.chat.ui.login.LoginActivity
import com.example.chat.ui.myRooms.MyRoomsFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        initViews()
    }
    private fun initViews() {

        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this

        observeOnLiveData()

    }

    private fun observeOnLiveData() {

        viewModel.homeEvents.observe(this){
            when(it!!){
                HomeNavigation.NavigateToLogin-> navigateToLogin()

                HomeNavigation.NavigateToAddRoom-> navigateToAddRoom()

                HomeNavigation.NavigateToMyRooms -> navigateToFragment(MyRoomsFragment())
                HomeNavigation.NavigateToBrowseRooms -> navigateToFragment(BrowseRoomsFragment())
            }
        }
    }

    private fun navigateToFragment(fragment:Fragment ) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }

    private fun navigateToAddRoom() {
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}

