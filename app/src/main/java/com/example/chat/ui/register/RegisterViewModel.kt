package com.example.chat.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.ui.Message
import com.example.chat.common.SingleLiveEvent
import com.example.chat.data.dataBase.SessionProvider
import com.example.chat.data.dataBase.fireStore.UsersDao
import com.example.chat.data.dataBase.model.User
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel: ViewModel() {
    val messageLiveData = SingleLiveEvent<Message>()
    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordConfirmation = MutableLiveData<String>()
    val showLoading = MutableLiveData(false)
    val showArrow = MutableLiveData(true)
    val userNameError = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val passwordConfirmationError = MutableLiveData<String>()
    val navigateLiveData =
        SingleLiveEvent<RegisterNavigation>()
    private val auth = FirebaseAuth.getInstance()

    fun register (){
        if (!validForm())
            return

        showLoading.value = true
        showArrow.value = false
        auth.createUserWithEmailAndPassword(email.value.toString(),password.value.toString())
            .addOnCompleteListener {task->
                if (task.isSuccessful){

                    val uid = task.result.user?.uid
                     insertAccountOnDataBase(uid)

                }else{
                    showLoading.value = false
                    showArrow.value = true
                   messageLiveData.value = Message(message = task.exception?.localizedMessage)
                }

            }
    }

    private fun insertAccountOnDataBase(uid: String?) {
        val user = User(uid,userName.value,email.value)

        UsersDao.saveAccountInFireStore(user){ task->
            if (task.isSuccessful){
                // show messageLiveData
                // save session provider
                showLoading.value = false
                showArrow.value = true
                SessionProvider.currentUser = user

                messageLiveData.value = Message(message = "Registered Successfully"
                , posMessage = "Ok", posAction = {
                    navigateToHome()
                    })

            }else{
                showLoading.value = false
                showArrow.value = true
                messageLiveData.value = Message(message = task.exception?.localizedMessage)
            }
        }
    }

    private fun navigateToHome() {
        navigateLiveData.value = RegisterNavigation.NavigateToHome
    }
     fun navigateToLogin() {
        navigateLiveData.value = RegisterNavigation.NavigateToLogin
    }

    private fun validForm(): Boolean {
        var isValid = true
        if (userName.value.isNullOrBlank()){
            isValid = false
            userNameError.value = "Invalid username "
        }
        if (email.value.isNullOrBlank()){
            isValid = false
            emailError.value = "Invalid email"
        }
        if (password.value.isNullOrBlank()){
            isValid = false
            passwordError.value = "Invalid password"
        }
        if (passwordConfirmation.value.isNullOrBlank()){
            isValid = false
            passwordConfirmationError.value = "Password confirmation doesn't match"
        }

        return isValid
    }

}