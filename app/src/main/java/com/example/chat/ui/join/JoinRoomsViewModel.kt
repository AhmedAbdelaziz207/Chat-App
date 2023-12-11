package com.example.chat.ui.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chat.common.SingleLiveEvent
import com.example.chat.data.dataBase.SessionProvider
import com.example.chat.data.dataBase.fireStore.RoomsDao
import com.example.chat.data.dataBase.model.Room
import com.example.chat.ui.Message

class JoinRoomsViewModel:ViewModel() {

    val roomLiveData = MutableLiveData<Room>()
    val joinRoomEvents = MutableLiveData<JoinNavigation>()
    val message = SingleLiveEvent<Message>()



    fun addMemberToRoom(){

        val uid = SessionProvider.currentUser?.uid
        RoomsDao.update(
            roomLiveData.value?.roomId!!,uid!!
        ){task->
            if (task.isSuccessful){
                navigateToChat()
                message.value = Message(message = "Joined Room Successfully")

            }else{
                message.value = Message(message = task.exception?.localizedMessage)
            }

        }

    }

    fun navigateToHome(){
        joinRoomEvents.value = JoinNavigation.NavigateToHome
    }

    private fun navigateToChat(){
        joinRoomEvents.value = JoinNavigation.NavigateToChat

    }
}