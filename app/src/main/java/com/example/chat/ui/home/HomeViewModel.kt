package com.example.chat.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {


    val homeEvents = MutableLiveData(HomeNavigation.NavigateToMyRooms)
    val myRoomsFragmentSelected = MutableLiveData(true)
    val browseFragmentSelected = MutableLiveData<Boolean>()

    fun navigateToAddRoom(){
        homeEvents.postValue(HomeNavigation.NavigateToAddRoom)
    }

    fun navigateToBrowseRooms(){
        browseFragmentSelected.value = true
        myRoomsFragmentSelected.value = false

        homeEvents.postValue(HomeNavigation.NavigateToBrowseRooms)
    }
    fun navigateToMyRooms(){
        myRoomsFragmentSelected.value = true
        browseFragmentSelected.value = false
        homeEvents.postValue(HomeNavigation.NavigateToMyRooms)

    }
     private fun navigateToLogin(){
        homeEvents.postValue(HomeNavigation.NavigateToLogin)
    }

    fun logout(){
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        navigateToLogin()
    }
}