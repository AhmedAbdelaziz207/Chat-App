package com.example.chat.ui.splash

import androidx.lifecycle.ViewModel
import com.example.chat.common.SingleLiveEvent
import com.example.chat.data.dataBase.SessionProvider
import com.example.chat.data.dataBase.fireStore.UsersDao
import com.example.chat.data.dataBase.model.User
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel:ViewModel() {
    val splashEvent = SingleLiveEvent<SplashNavigation>()

    fun redirect(){
        if (UsersDao.isSignedIn()){
            setSessionProvider()
        }else{
            splashEvent.postValue(SplashNavigation.NavigateToLogin)
        }
    }

    private fun setSessionProvider(){
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        UsersDao.getUserFromFireStore(uid!!){task->
            if (task.isSuccessful)
                SessionProvider.currentUser = task.result.toObject(User::class.java)
                 splashEvent.postValue(SplashNavigation.NavigateToHome)
        }

    }
}