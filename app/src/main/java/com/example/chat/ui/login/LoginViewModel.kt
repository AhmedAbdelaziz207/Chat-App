package com.example.chat.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.ui.Message
import com.example.chat.common.SingleLiveEvent
import com.example.chat.data.dataBase.SessionProvider
import com.example.chat.data.dataBase.fireStore.UsersDao
import com.google.firebase.auth.FirebaseAuth
import com.example.chat.data.dataBase.model.User
import java.lang.Exception

class LoginViewModel:ViewModel() {
    val email = MutableLiveData<String>("ahmed.abdelaziz@gmail.com")
    val password = MutableLiveData<String>("123456")
    val messageLiveData = MutableLiveData<Message>()
    val showLoading = MutableLiveData(false)
    val showArrow = MutableLiveData(true)
    val emailError = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val navigateLiveData = SingleLiveEvent<LoginNavigation>()
    private val auth = FirebaseAuth.getInstance()

    fun login(){

        if (!validLogin())
            return

        showLoading.value = true
        showArrow.value = false

        auth.signInWithEmailAndPassword(email.value!!,password.value!!)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    handleSuccess(task.result.user?.uid?:"")

                }else{
                    handleFailure(task.exception)
                }
            }
    }

    private fun handleFailure(exception: Exception?) {
        showLoading.value = false
        showArrow.value = true
        messageLiveData.value = Message(message = exception?.localizedMessage
        , posMessage ="Ok" )

    }

    private fun handleSuccess(uid : String) {
        showLoading.value = false
        showArrow.value = true
        messageLiveData.value = Message(message = "LogIn Successful"
                    , posMessage = "OK", posAction = {
                        setSessionProvider(uid)
                        navigateToHome()
            })

    }

    private fun navigateToHome(){
        navigateLiveData.value = LoginNavigation.NavigateToHome
    }
    fun navigateToRegister(){
        navigateLiveData.value = LoginNavigation.NavigateToRegister
    }

    private fun setSessionProvider(uid: String) {

        UsersDao.getUserFromFireStore(uid){task->
            if (task.isSuccessful)
            SessionProvider.currentUser = task.result.toObject(User::class.java)
            else{
                messageLiveData.value= Message(message = "Failed to load data")
            }
        }
    }


    private  fun validLogin():Boolean{
        var isValid = true
      if (email.value.isNullOrBlank() && email.value.isNullOrBlank() ){
          emailError.value = "Enter valid Email"
          passwordError.value = "Enter valid Password"
            isValid = false
      }
        else if (email.value.isNullOrBlank()){
            emailError.value = "Enter valid Email"
            isValid = false
        }else if (email.value.isNullOrBlank()){
            passwordError.value = "Enter valid Password"
            isValid = false
        }
        return isValid
    }
}